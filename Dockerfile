FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . /app
RUN mvn clean package -Dspring-boot.run.profiles=production -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
RUN ln -s -f /usr/share/zoneinfo/America/Fortaleza /etc/localtime

COPY --from=builder /app/target/*.jar /app/invflorestal.jar
EXPOSE 42069
ENTRYPOINT ["java", "--enable-preview", "-Dspring.profiles.active=production", "-jar", "/app/invflorestal.jar"]