package edu.ucr.cs.cs167.kmorr008
import edu.ucr.cs.bdlab.beast.geolite.{Feature, IFeature}
import org.apache.spark.SparkConf
import org.apache.spark.beast.SparkSQLRegistration
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

import scala.collection.Map

import scala.collection.Map

/**
 * Scala examples for Beast
 */
object BeastScala {
  def main(args: Array[String]): Unit = {
    // Initialize Spark context

    val conf = new SparkConf().setAppName("Beast Example")
    // Set Spark master to local if not already set
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")

    val spark: SparkSession.Builder = SparkSession.builder().config(conf)

    val sparkSession: SparkSession = spark.getOrCreate()
    val sparkContext = sparkSession.sparkContext
    SparkSQLRegistration.registerUDT
    SparkSQLRegistration.registerUDF(sparkSession)

    val operation: String = args(0)
    val inputFile: String = args(1)
    try {
      // read the input file as TSV
      val input = sparkSession.read.format("csv")
        .option("sep", "\t")
        .option("inferSchema", "true")
        .option("header", "true")
        .load(inputFile)
      // Import Beast features
      import edu.ucr.cs.bdlab.beast._
      val t1 = System.nanoTime()
      var validOperation = true

      operation match {
        case "count-by-zip" =>
        // TODO count the total number of crimes for each zipcode and display on the screen

        // load the parquet file
          val dataset = sparkSession.read.parquet(inputFile)
            .createOrReplaceTempView("crimes")
        // query to compute crimes per zipcode
          sparkSession.sql(
            s"""
            SELECT ZIPCode, count(*) AS count
            FROM crimes
            GROUP BY ZIPCode
          """).createOrReplaceTempView("zip_counts")

        //Load the ZIP Code dataset using Beast and convert it to an RDD. (***Dataframe???)
        //val zipRDD: SpatialRDD = sparkContext.shapefile("tl_2018_us_zcta510.zip")
          sparkContext.shapefile("tl_2018_us_zcta510.zip")
            .toDataFrame(sparkSession)
            .createOrReplaceTempView("zipcodes")

        // create an output file
        val outputFile: String = args(2)

        // Join the two datasets using an equi-join query on the attributes ZIPCode and ZCTA5CE10
        val joinedData = sparkSession.sql(s"""SELECT ZIPCode, g, count
                                             |FROM zip_counts, zipcodes
                                             |WHERE ZIPCode = ZCTA5CE10""".stripMargin)
          joinedData.toSpatialRDD.coalesce(1).saveAsShapefile(outputFile)


        // TODO write a Shapefile that contains the count of the crimes by zip code
        case _ => validOperation = false

      }

      val t2 = System.nanoTime()
      if (validOperation)
        println(s"Operation '$operation' on file '$inputFile' took ${(t2 - t1) * 1E-9} seconds")
      else
        Console.err.println(s"Invalid operation '$operation'")
    }
    finally {
      sparkSession.stop()
    }
  }
}