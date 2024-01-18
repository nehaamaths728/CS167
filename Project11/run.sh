#!/usr/bin/env sh
mvn clean package

spark-submit target/*.jar write-parquet Crimes_-_2001_to_present.csv cc.parquet

spark-submit --class Spark.crimeStats target/ count-by-zip Chicago_Crimes_ZIP.parquet

spark-submit --class Spark.crimeStats target/project_task3-1.0-SNAPSHOT.jar Chicago_Crimes_10K.csv_file.parquet 10/07/2005 06/07/2007
