package exercise1

import org.apache.spark.SparkConf
import org.apache.spark.sql. SparkSession

object Spark {

  private val appName: String = "exercise1"

  val conf: SparkConf = startSparkConf
  val spark: SparkSession = startSparkSession

  def startSparkConf : SparkConf = {
    new SparkConf()
      .set("spark.app.name", appName)
      .setMaster("local[*]")
    //TODO: setup spark for production environment
  }

  def startSparkSession : SparkSession = {
    SparkSession.builder()
      .config(conf)
      .getOrCreate()
  }

}
