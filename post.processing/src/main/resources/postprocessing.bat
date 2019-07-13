@echo OFF

java -jar -Dlog4j.configuration=file:log4j.xml target/postprocessing.jar -a 100 -c 1
pause