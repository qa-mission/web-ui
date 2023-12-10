#! /bin/sh
java -Dlog4j.configurationFile=log4j2.xml -Denv=$1 -jar bin/qamission-web-ui.jar $2

