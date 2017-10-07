#!/bin/bash

thrift --gen java isalive.thrift
mv gen-java/* ./code

cd ./code
mvn package

cd target
java -jar some-artifact-1.0.0-SNAPSHOT-jar-with-dependencies.jar
