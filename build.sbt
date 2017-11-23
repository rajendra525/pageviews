//enablePlugins(JavaAppPackaging)

name := "PageViews_LinkListed"
organization := "com.mycompany"
version := "1.0"
scalaVersion := "2.10.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-target:jvm-1.7")

libraryDependencies ++= {
  val sparkV      = "2.1.1"
  Seq(
    "org.apache.spark"  %% "spark-core" % sparkV,
    "org.apache.spark"  %% "spark-sql"  % sparkV,
    "org.apache.spark"  %% "spark-yarn" % sparkV,
    "com.databricks"    %% "spark-avro" % "3.2.0",
    "com.databricks"    %% "spark-csv"  % "1.5.0"
  )
}



