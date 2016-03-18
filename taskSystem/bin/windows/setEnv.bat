@echo off

Rem JAVA環境変数を定義する
set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_79
set PATH=%JAVA_HOME%\bin

Rem クラスパス
set CLASSPATH=conf\;lib\*

exit /B 0
