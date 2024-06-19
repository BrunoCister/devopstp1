FROM openjdk:21-oracle
COPY target/DevOpsTP1-1.0.0-SNAPSHOT.jar DevOpsTP1-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "./DevOpsTP1-1.0.0-SNAPSHOT.jar"]