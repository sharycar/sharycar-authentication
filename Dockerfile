FROM openjdk:8-jre-alpine
RUN mkdir /app
WORKDIR /app
ADD ./authentication-api/target/sharycar-authentication-api-1.0.0.jar /app
EXPOSE 8082
CMD ["java", "-jar", "sharycar-authentication-api-1.0.0.jar"]
