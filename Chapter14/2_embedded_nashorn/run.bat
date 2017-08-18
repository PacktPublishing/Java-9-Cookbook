@echo off
javac -d mods --module-source-path src src\embedded.nashorn\com\packt\*.java src\embedded.nashorn\module-info.java

if %errorlevel% == 1 goto failedCompilation

:runCode
java -p mods -m embedded.nashorn/com.packt.EmbeddedNashornDemo
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'
