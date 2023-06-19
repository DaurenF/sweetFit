# Build stage
FROM maven AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Runtime stage
FROM openjdk:17 AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar

# Nginx stage
FROM nginx
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=runtime /app/app.jar .
EXPOSE 9000
CMD service nginx start && java -jar app.jar
