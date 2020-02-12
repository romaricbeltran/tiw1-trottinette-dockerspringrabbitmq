FROM maven:3.6.3-jdk-11 
WORKDIR /banque  

ARG JAR_FILE=target/banque*.jar 
COPY ${JAR_FILE} banque.jar 
COPY run.sh run.sh 
RUN ["chmod", "+x", "run.sh"]  

EXPOSE 9090 
CMD ["./run.sh"]
