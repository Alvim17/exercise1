name := "exercise1"
version := "0.1"
scalaVersion := "2.12.14"

libraryDependencies ++= Seq(
  // Spark libs
  "org.apache.spark" %% "spark-core" % "3.3.0",
  "org.apache.spark" %% "spark-sql" % "3.3.0"
)