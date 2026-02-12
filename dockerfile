# Use official Java 21 image
FROM eclipse-temurin:21-jdk-jammy

# Set workdir
WORKDIR /app

# Copy Maven pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN apt-get update && apt-get install -y maven
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Package the application
RUN mvn package -DskipTests

# Run the app
CMD ["java", "-jar", "target/animestream-0.0.1-SNAPSHOT.jar"]