@echo off

Rem ���ϐ��ǂݍ���
cd  %~dp0\..
call bin\setEnv.bat

Rem ���s
java -cp "%CLASSPATH%" jp.co.sac.routineTaskSystem.main.TaskSystem %*

exit /b %errorlevel%

