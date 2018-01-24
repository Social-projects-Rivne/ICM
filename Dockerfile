FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD target/issue-city-monitor-0.0.1-SNAPSHOT.jar issue-city-monitor.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","issue-city-monitor.jar"]