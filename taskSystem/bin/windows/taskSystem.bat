@echo off

Rem 環境変数読み込み
cd  %~dp0\..
call bin\setEnv.bat

Rem 実行
java -cp "%CLASSPATH%" jp.co.sac.routineTaskSystem.main.TaskSystem %*

exit /b %errorlevel%

