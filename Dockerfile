FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B
EXPOSE 8080
CMD ["java","-jar","target/ledgerpro-1.0.0.jar"]