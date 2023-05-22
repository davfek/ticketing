FROM openjdk:17-alpine
COPY /Docker/demo-0.0.1-SNAPSHOT.jar /demo.jar
CMD ["java", "-jar", "demo.jar"]