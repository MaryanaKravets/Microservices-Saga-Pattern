#!/bin/bash
# desc: monitor service running state

while [ 1 ]
do

	status_code=$(curl --write-out %{http_code} --silent --output /dev/null http://localhost:8888/actuator/health )

        if [[ "$status_code" -ne 200 ]] ; then
        echo "Site status changed to $status_code" 
        sleep 5
	status_code2=$(curl --write-out %{http_code} --silent --output /dev/null http://localhost:8888/actuator/health )
	while [ "$status_code2" -ne 200 ] 
	do
	status_code2=$(curl --write-out %{http_code} --silent --output /dev/null http://localhost:8888/actuator/health )
	echo "Status code $status_code2" 
	sleep 5
	done
	exit 0

	else
	  echo "Status code is 200"
	sleep 5
	java  -jar ApiGateway.jar
  	exit 0
	fi
done

#url=$0
#curl ${url} -I -o headers -s
#cat  headers
#cat headers | head -n 1 | cut '-d ' '-f2'

