#!/usr/bin/env bash

current_path=$(pwd)

case "$(uname)" in
	Linux)
		bin_abs_path=$(readlink -f $(dirname $0))
		;;
	*)
		bin_abs_path=$(cd $(dirname $0); pwd)
		;;
esac

base=${bin_abs_path}/..
conf=${base}/conf/server.properties
log4j=${base}/conf/log4j.properties

export LANG=en_US.UTF-8
export BASE=${base}

## set java path
if [ -z "$JAVA" ] ; then
    JAVA=$(which java)
fi

if [ -z "$JAVA" ] ; then
    echo "cannot find a java jdk" 2>&2
    exit 1
fi

str=$(file $JAVA_HOME/bin/java | grep 64-bit)
if [ -n "$str" ] ; then
    JAVA_OPTS="-server -Xms2048m -Xmx3072m -Xmn1024m -XX:SurvivorRatio=2 -XX:PermSize=96m -XX:MaxPermSize=256m -Xss256k -XX:-UseAdaptiveSizePolicy -XX:MaxTenuringThreshold=15 -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError"
else
    JAVA_OPTS="-server -Xms1024m -Xmx1024m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:MaxPermSize=128m"
fi
JAVA_OPTS=" $JAVA_OPTS -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8"
TRACKER_OPTS="-DappName=spUpload -Dconfig.server=$conf -Dconfig.log4j=$log4j"

filepath=$1

if [ -z "$filepath" ] ; then
    echo "Usage: $0 {FilePath}"
    exit 1
fi

if [ -e $conf ] ; then
    for i in ${base}/lib/*;
    do CLASSPATH=${i}:"$CLASSPATH";
    done
    for i in ${base}/conf/*;
        do CLASSPATH=${i}:"$CLASSPATH";
    done

    cd ${base}

    $JAVA $JAVA_OPTS $TRACKER_OPTS -classpath .:$CLASSPATH com.github.hackerwin7.shrimp.executors.UploadExecutor $filepath 2>&1

    echo "starting upload..."
else
    echo "conf $conf is not exists!"
fi