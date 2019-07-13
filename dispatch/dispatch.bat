@echo OFF

REM set amount=%1
REM set chunksize=%2

java -jar -Dlog4j.configuration=file:log4j.xml target/dispatch.jar -ju ralf.nowicki -jp ".&Venus4/321" -jf default-dispatch-jobs.json -mus root -mpa pass -debug
pause