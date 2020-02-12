# getting base image maven
FROM maven:latest

COPY . /root

RUN cd /root/maintenance-web
RUN mvn clean test

CMD cd /root/maitenance-web/ && mvn jetty:run
