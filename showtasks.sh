#!/bin/bash

if sh ./runcrud.sh; then
  echo "Everything went smooth"
  open -a Safari http://localhost:8080/crud/v1/tasks
else
  echo "Cannot run crud"
  fail
  exit
fi
