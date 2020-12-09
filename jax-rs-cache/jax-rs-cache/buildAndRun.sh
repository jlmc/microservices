#!/bin/sh
mvn clean package && docker build -t io.github.jlmc/jax-rs-cache .
docker rm -f jax-rs-cache || true && docker run -d -p 8080:8080 -p 4848:4848 --name jax-rs-cache io.github.jlmc/jax-rs-cache 
