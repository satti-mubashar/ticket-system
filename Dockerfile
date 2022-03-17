FROM openjdk:8-jdk

COPY /target/AuthApp-0.0.1-SNAPSHOT.jar /data/ticket-system/AuthApp-0.0.1-SNAPSHOT.jar

WORKDIR /data/ticket-system

RUN java -version

CMD ["java","-jar","AuthApp-0.0.1-SNAPSHOT.jar"]

EXPOSE 4001-4001