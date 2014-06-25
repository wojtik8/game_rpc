#!/bin/bash

echo Generating headers

IDL_DIR=.
CLIENT_GEN_DIR=../game_client/src
SERVER_GEN_DIR=../game_server/node
NODEJS_GEN=./gen-nodejs
JAVA_GEN=./gen-java

report_failure()
{
	echo failure $1
	exit 1
}
for f in $IDL_DIR/*.thrift
do
	echo processing file $f
	thrift -r --gen java $f || report_failure compilation
	thrift -r --gen js:node $f || report_failure compilation
done

cp -rf $NODEJS_GEN $SERVER_GEN_DIR || report_failure copy_server
cp -rf $JAVA_GEN $CLIENT_GEN_DIR || report_failure copy_client

echo Headers generated successfully
