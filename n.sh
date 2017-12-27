
#/bin/bash

if [ $# -eq 0 ]
  then
    echo "No arguments supplied"
else

  if [ $1 = "b" ]; then
     nginx -p $(pwd) -c $(pwd)/nginx.conf
  fi
  if [ $1 = "e" ]; then
     nginx -s stop
  fi
fi
