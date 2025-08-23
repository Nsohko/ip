@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete local file storage to test creation
if exist .\ChalkData\ rmdir /s /q .\ChalkData

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin Chalk < ./inputs/input1.txt > ACTUAL.TXT

REM run the program again, append results to ACTUAL.TXT to test file storage
java -classpath ..\bin Chalk < ./inputs/input2.txt >> ACTUAL.TXT

REM compare the output to the expected output on first run
FC ACTUAL.TXT EXPECTED.TXT
