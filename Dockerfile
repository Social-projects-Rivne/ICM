FROM openjdk:8
MAINTAINER rv-028Java
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/pom.xml
COPY src /usr/local/src
WORKDIR COPY /usr/local
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ICM.jar"]