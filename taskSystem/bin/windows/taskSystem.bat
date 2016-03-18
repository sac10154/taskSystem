@echo off

Rem ŠÂ‹«•Ï”“Ç‚İ‚İ
cd  %~dp0\..
call bin\setEnv.bat

Rem Às
java -cp "%CLASSPATH%" jp.co.sac.routineTaskSystem.main.TaskSystem %*

exit /b %errorlevel%

