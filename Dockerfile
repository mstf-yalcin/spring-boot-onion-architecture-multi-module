FROM openjdk:17-slim as build
WORKDIR /app
COPY  . .

RUN chmod +x ./presentation/onion.api/mvnw && \
    ./presentation/onion.api/mvnw package -DskipTests

FROM eclipse-temurin:17-jre-alpine AS jre
WORKDIR /app
COPY --from=build /app/presentation/onion.api/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
