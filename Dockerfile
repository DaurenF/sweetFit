# Build stage
FROM maven AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -o install
COPY src ./src
RUN mvn package -DskipTests

# Runtime stage
FROM openjdk:17 AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
