rem %1 is the <environment name>  in  <root>/resources/env/<environment name>
rem %2 is TestNG suite xml file to run tests from.
java -Dlog4j.configurationFile=log4j2.xml -Denv=%1 -jar bin/qamission-web-ui.jar %2
