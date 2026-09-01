#!/bin/sh
# Licensed to the Apache Software Foundation (ASF)
# Maven Wrapper

# set -e

PRG="$0"
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`"/$link"
  fi
done

DIRNAME=`dirname "$PRG"`
SCRIPT_NAME=`basename "$0"`

# the root of the project
APP_HOME=`cd "$DIRNAME" && pwd`

# Download Maven if not present
if [ ! -f "$APP_HOME/.mvn/wrapper/maven-wrapper.jar" ]; then
    echo "Downloading Maven wrapper..."
    mkdir -p "$APP_HOME/.mvn/wrapper"
    curl -fsSL "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.1.0/maven-wrapper-3.1.0.jar" -o "$APP_HOME/.mvn/wrapper/maven-wrapper.jar"
fi

# Use default maven config if it exists
if [ -f "$APP_HOME/.mvn/jvm.config" ] ; then
    DEFAULT_JVM_OPTS=`cat "$APP_HOME/.mvn/jvm.config" | xargs`
fi

if [ -z "$JAVA_HOME" ] ; then
    JAVA_EXE="java"
else
    JAVA_EXE="$JAVA_HOME/bin/java"
fi

if ! command -v $JAVA_EXE &> /dev/null; then
    echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH."
    exit 1
fi

exec "$JAVA_EXE" $DEFAULT_JVM_OPTS $JAVA_OPTS $MAVEN_OPTS -classpath "$APP_HOME/.mvn/wrapper/maven-wrapper.jar" "-Dmaven.home=$APP_HOME/.mvn" "-Dmaven.multiModuleProjectDirectory=$APP_HOME" org.apache.maven.wrapper.MavenWrapperMain "$@"
