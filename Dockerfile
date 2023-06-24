FROM openjdk:17-alpine
COPY /Docker/ticketing-0.0.1-SNAPSHOT.jar /demo.jar
ENV DB_URL=jdbc:postgresql://postgres:5432/postgres
EXPOSE 8082
CMD ["java", "-jar", "demo.jar"]