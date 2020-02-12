FROM maven:3.6.3-jdk-11-openj9 as MAVEN_BUILD  

COPY ./ ./banque/  

WORKDIR /banque  

RUN mvn package  

FROM openjdk:11-jdk   

COPY --from=MAVEN_BUILD /banque/target/ . 

ENTRYPOINT ["java","-jar","banque-0.0.1-SNAPSHOT.jar"]
