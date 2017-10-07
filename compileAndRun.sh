#!/bin/bash

thrift --gen java isalive.thrift
mv gen-java/* ./code/src/main/java

cd ./code
# build, using the thrift version you have installed.
thrift --version | awk '{print $3}' | xargs bash -c 'mvn package -Dthrift.version=$0'

cd target
java -jar some-artifact-1.0.0-SNAPSHOT-jar-with-dependencies.jar
