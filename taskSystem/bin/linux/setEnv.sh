#!/bin/sh

# JAVA環境変数を定義する
JAVA_HOME=/usr/lib/jvm/java-7-openjdk-i386
PATH=$JAVA_HOME/bin:$PATH
CLASSPATH=conf:lib/*

export JAVA_HOME
export PATH
export CLASSPATH

