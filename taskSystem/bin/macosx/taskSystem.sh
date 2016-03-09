#!/bin/sh

# 環境変数読み込み
. bin/setEnv.sh

while [ "$1" != "" ]
do
	echo "---------------------------------------"
	echo "`date +"%Y/%m/%d %H:%M:%S"`[START]$1"
	java -cp $CLASSPATH jp.co.sac.routineTaskSystem.main.TaskSystem $1
	echo "`date +"%Y/%m/%d %H:%M:%S"`[END]$1"
	shift
done

