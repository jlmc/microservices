#!/bin/sh
mvn clean package && docker build -t io.github.jlmc/self-inject .
docker rm -f self-inject || true && docker run -d -p 8080:8080 -p 4848:4848 --name self-inject io.github.jlmcself-inject
