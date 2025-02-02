FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.security.oauth2.client.registration.ehrbase_test_client.redirect-uri=https://ipsdemo.collabrathon.fii.people.aws.dev/login/oauth2/code/ehrbase_test_client"]
