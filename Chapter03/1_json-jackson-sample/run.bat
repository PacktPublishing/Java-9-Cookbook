@echo off
"%JAVA8_HOME%"\bin\javac -version
rmdir /q /s classes
mkdir classes
"%JAVA8_HOME%"\bin\javac -cp lib\*;classes -d classes src\com\packt\*.java src\com\packt\model\*.java

if %errorlevel% == 1 goto failedCompilation

:runCode
"%JAVA8_HOME%"\bin\java -cp lib\*;classes com.packt.Sample
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'