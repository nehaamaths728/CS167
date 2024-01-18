
package Spark;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.text.ParseException;

import static org.apache.spark.sql.functions.*;

public class crimeStats {

    static String start_dt;
    static String end_dt;

    public static void main(String[] args) throws ParseException {

      //  start_dt = "10/07/2005";
      //  end_dt = "06/07/2007";
        start_dt = args[0];
        end_dt = args[1];

        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL data source Parquet example")
                .master("local[2]")
                .getOrCreate();


        String parquet_file = "/Users/jijimathews/Desktop/jijimathews/cs167/workspace/project_task3/Chicago_Crimes_10K.csv_file.parquet";

        String output_dir = "/Users/jijimathews/Desktop/jijimathews/cs167/workspace/project_task3/CrimeTypeCount";

        Dataset<Row> parquet_data = spark.read().parquet(parquet_file);

        // The inferred schema can be visualized using the printSchema() method
        System.out.println("Schema\n=======================");
        parquet_data.printSchema();


        // Creates a temporary view using the DataFrame
        parquet_data.createOrReplaceTempView("parquet_data");


        String query = "SELECT to_timestamp(Date, 'MM/dd/yyyy hh:mm:ss a') as crime_date, PrimaryType FROM parquet_data WHERE to_date(Date, 'MM/dd/yyyy hh:mm:ss a') BETWEEN to_date('"+ start_dt +"', 'MM/dd/yyyy') AND to_date('"+ end_dt +"', 'MM/dd/yyyy')";
        Dataset<Row> parquetDF = spark.sql(query);

        System.out.println("\n\nSQL Result\n=======================");
        parquetDF.show();

        parquetDF.cache()
                .groupBy("PrimaryType")
                .agg(count(lit(1)).alias("count"))
                .orderBy(col("count").desc()) //Optional
                //.limit(20) //Optional
                .coalesce(1) // To combine all dataframes into a single file before saving it in the directory
                .write().option("header","true").csv(output_dir);

        spark.stop();
    }

}