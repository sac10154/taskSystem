@echo off

Rem JAVA環境変数を定義する
set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_65
set PATH=C:\Program Files\Java\jdk1.7.0_65\bin

Rem クラスパス
set CLASSPATH=conf\;dist\taskSystem.jar;dist\lib\*

exit /B 0
