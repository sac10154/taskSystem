#!/bin/sh

# JAVA環境変数を定義する
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home
PATH=$JAVA_HOME/bin:$PATH
CLASSPATH=conf:lib/*

export JAVA_HOME
export PATH
export CLASSPATH

