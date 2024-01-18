#!/usr/bin/env sh
mvn clean package

spark-submit --class Spark.crimeStats target/ count-by-zip Chicago_Crimes_ZIP.parquet