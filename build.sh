#!/bin/bash
docker-compose up -d mysql-master mysql-slave rabbitmq
sleep 10
mvn clean install
docker-compose down
