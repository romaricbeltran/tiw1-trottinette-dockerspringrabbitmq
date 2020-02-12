# getting base image maven
FROM maven:latest

ADD ./root/ /root/

EXPOSE 8080

RUN cd /root/maintenance-web && mvn clean install -DskipTests

CMD cd /root && mvn jetty:run
