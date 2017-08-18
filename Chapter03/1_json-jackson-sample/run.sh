javac -cp 'lib/*' -d classes -sourcepath src $(find src -name *.java)
java -cp lib/*:classes/ com.packt.Sample