package com.mycompany.pageviews

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Transformation {
  def main(args: Array[String]) {

    // Comment below line when packaging for AWS EMR cluster.
    //System.setProperty("hadoop.home.dir", "C:\\spark-2.1.1-bin-hadoop2.7\\spark-2.1.1-bin-hadoop2.7");

    val conf = new SparkConf().setAppName("PageViews").set("spark.kryoserializer.buffer.max", "1024")
      .set("spark.dynamicAllocation.enabled", "true")
      .set("spark.shuffle.service.enabled", "true")
      //.setMaster("local[2]")

    val spark = SparkSession
      .builder()
      .appName("PageViews")
      .config(conf)
      .getOrCreate()

    /*val input_path = "C:\\CreditApp\\pageViews.csv"
    val output_path = "C:\\CreditApp\\pageViewsLinkListed\\"*/
      
    val input_path = args(0).trim
    val output_path = args(1).trim

    val data_df = spark.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true") 
      .load(input_path)

    val table1 = data_df.createOrReplaceTempView("pageviews")
    val sequencedViews_df = spark.sql("SELECT * FROM ( select *, dense_rank() over (partition by domain_userid order by collector_tstamp ASC) as rank from pageviews)")

    val table2 = sequencedViews_df.createOrReplaceTempView("extendedPageViews")
    val extendedPageViews_df = spark.sql("select p1.event_id, p1.collector_tstamp, p1.domain_userid, p1.page_urlpath, p1.rank, p2.event_id as next_event_id from extendedPageViews p1 INNER JOIN extendedPageViews p2 ON p2.rank = p1.rank+1 and p1.domain_userid = p2.domain_userid order by p1.domain_userid, p1.rank ")

    extendedPageViews_df.drop("rank").coalesce(1).write
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .save(output_path)

    spark.close()

  }
}