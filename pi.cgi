#!/bin/sh

echo Content-type: text/text
echo 
echo

runner="cheng.yan.pi.Runner"
arg="`echo $QUERY_STRING | /bin/sed 's/\&/ /g'`"

echo "me: `whoami`"
echo $runner
echo $arg
echo '--------------------'

cd /home/pi/workspace/project1/Pi1/bin
echo /home/pi/workspace/project1/Pi1/bin

echo java -cp .:classes:/opt/pi4j/lib/* $runner $arg
java -cp .:classes:/opt/pi4j/lib/* $runner $arg

echo '--------------------'

