FROM openjdk:17
WORKDIR /app
COPY target/fit-0.0.1-SNAPSHOT.jar /app
CMD ["java", "-jar", "fit-0.0.1-SNAPSHOT.jar"]
