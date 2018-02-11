#!/bin/sh
mvn clean package && docker build -t org.xine/restfull-docs .
docker rm -f restfull-docs || true && docker run -d -p 8080:8080 -p 4848:4848 --name restfull-docs org.xine/restfull-docs 
