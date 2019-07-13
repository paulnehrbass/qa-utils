@echo OFF

java -jar -Dlog4j.configuration=file:log4j.xml target/postprocessing.jar -a 1000 -c 1 -u system -p system
pause