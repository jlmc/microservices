#!/bin/sh
mvn clean package && docker build -t io.github.jlmc/sse-service .
docker rm -f sse-service || true && docker run -d -p 8080:8080 -p 4848:4848 --name sse-service io.github.jlmc/sse-service 
