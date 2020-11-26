#!/bin/sh
mvn clean package && docker build -t io.github.jlmc/jax-rs-subresources .
docker rm -f jax-rs-subresources || true && docker run -d -p 8080:8080 -p 4848:4848 --name jax-rs-subresources io.github.jlmc/jax-rs-subresources 
