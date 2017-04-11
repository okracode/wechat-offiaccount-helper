#!/bin/bash
rval=0


#get the program dir
PRG="$0"
progname=`basename "$0"`

# need this for relative symlinks
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
    else
    PRG=`dirname "$PRG"`"/$link"
    fi
done

cd `dirname "$PRG"`/..

CMD="java -Xmx1024m -Xms384m -XX:PermSize=128M -XX:MaxPermSize=256m -Djava.ext.dirs=lib -cp conf:`ls *.jar` -jar wechat-intfc.war"


case $1 in
'start')
        nohup $CMD &
        echo "$0 has started"
	;;

'stop')

	kill -9 `ps -ef|grep $MAIN_CLASS |awk '{print $2}'`
        echo "$0 has stopped"
	;;

'status')
	if [ `ps -ef|grep -c $MAIN_CLASS` -ge "1" ]; then
                echo "$0 has started"
        else
                echo "$0 has stopped"
        fi
        ;;

'restart')

	kill -9 `ps -ef|grep $MAIN_CLASS |awk '{print $2}'`
	nohup $CMD &
	;;

*)
        echo "usage:$0{start|stop|restart|status}"
        rval=1
        ;;
esac

exit $rval
