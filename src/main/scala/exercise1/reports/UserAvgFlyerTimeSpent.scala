package exercise1.reports

import exercise1.App
import exercise1.Spark._
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.{col, dense_rank, sum}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.DataTypes.{LongType, TimestampType}
import org.apache.spark.sql.types.DateType

object UserAvgFlyerTimeSpent extends App {

  /*  since we don't have an event for when the user leaves the application,
      this parameter is used in cases where the user has a "flyer_open" event
      but the next sequential event is too far ahead in time, meaning that during
      this period the user is possibly not interacting with the application anymore
      and, therefore, not spending time on the flyer */
  val user_timeout_limit = 900 //in seconds

  override def pipeline(): Unit = {

    // reading .csv input
    val df_initial = spark.read
      .option("header", "true")
      .csv("data/exercise1/input")
      .distinct()
      .withColumn("timestamp", col("timestamp").cast(TimestampType))

    // ordering each user's events by timestamp
    val df_ordered = df_initial
      .withColumn("rank", dense_rank().over(
        Window.partitionBy("user_id").orderBy("timestamp")
      ))
      .distinct()

    /* estimating time spent on each flyer by doing the timestamp
    difference between two sequential events */
    val df_time_spent = df_ordered.as("a").join(
      df_ordered.as("b"),
      col("a.user_id") === col("b.user_id") &&
        col("a.rank") + 1 === col("b.rank"),
      "left"
      )
      .select(
        col("a.*"),
        (col("b.timestamp").cast(LongType) - col("a.timestamp").cast(LongType))
          .as("time_spent")
      )
      .distinct()
      .filter(col("time_spent") <= user_timeout_limit)
      .filter(col("event").isin("flyer_open", "item_open", "favorite"))

    // aggregating the daily time spent (in seconds) on each flyer per user
    val df_final = df_time_spent
      .groupBy(
        col("a.timestamp").cast(DateType).as("date"),
        col("merchant_id"),
        col("flyer_id"),
        col("user_id")
      )
      .agg(sum("time_spent").as("time_spent_seconds"))

    // writing output in .csv format
    df_final.write
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .csv("data/exercise1/output/csv")

    // writing output in .parquet format
    df_final.write
      .mode(SaveMode.Overwrite)
      .parquet("data/exercise1/output/parquet")
  }
}
