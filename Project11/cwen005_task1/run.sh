#!/usr/bin/env sh
mvn clean package

spark-submit target/*.jar write-parquet Crimes_-_2001_to_present.csv cc.parquet
