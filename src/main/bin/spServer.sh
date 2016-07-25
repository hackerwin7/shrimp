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
TRACKER_OPTS="-DappName=spServer -Dconfig.server=$conf -Dconfig.log4j=$log4j"


function start() {
    if [ -f $base/bin/server.pid ] ; then
        echo "found server.pid , please stop it first." 2>&2
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

        $JAVA $JAVA_OPTS $TRACKER_OPTS -classpath .:$CLASSPATH com.github.hackerwin7.shrimp.executors.ServerExecutor 1>>$base/logs/server.log 2>&1 &
        echo $! > $base/bin/server.pid

        echo "server started."
    else
        echo "conf $conf is not exists!"
    fi
}

function get_pid() {
        STR=$1
        PID=$2
        if $cygwin ; then
            JAVA_CMD="$JAVA_HOME/bin/java"
            JAVA_CMD=$(cygpath --path --unix $JAVA_CMD)
            JAVA_PID=$(ps | grep $JAVA_CMD | awk '{print $1}')
        else
            if [ ! -z "$PID" ] ; then
                JAVA_PID=$(ps -C java -f --width 1000 | grep "$STR" | grep "$PID" -v grep | awk '{print $2}')
            else
                JAVA_PID=$(ps -C java -f --width 1000 | grep "$STR" | grep -v grep | awk '{print $2}')
            fi
        fi
        echo $JAVA_PID;
    }

function stop() {
    cygwin=false
    case "$(uname)" in
        CYGWIN*)
            cygwin=true
            ;;
    esac

    base=$(dirname $0)/..
    pidfile=$base/bin/server.pid
    if [ ! -f "$pidfile" ] ; then
        echo "server is not running. can not stop it!"
        exit
    fi

    pid=$(cat $pidfile)
    if [ "$pid" == "" ] ; then
        pid=$(get_pid "appName=spServer")
    fi

    echo -e "$(hostname): stopping server $pid ... "
    kill $pid

    LOOPS=0
    while (true);
    do
        gpid=$(get_pid "appName=spServer" "$pid")
        if [ "$gpid" == "" ] ; then
            echo "ok! killed!!"
            $(rm $pidfile)
            break;
        fi
        let LOOPS=LOOPS+1
        sleep 1
    done
}

case $1 in
start)
    echo "starting shrimp server ... "
    start
    ;;
stop)
    echo "stopping shrimp server ..."
    stop
    ;;
*)
    echo "Usage: $0 {start|stop}"
    exit
    ;;
esac