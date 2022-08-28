#
# Build stage
#
#FROM maven:3-alpine AS build
#WORKDIR /authentication
#COPY . .
#RUN mvn clean package -DskipTests -P FullStack


# Server stage
# 
FROM tomcat:8.5-jre8
WORKDIR /usr/local/tomcat
RUN rm -rf /usr/local/tomcat/webapps/ROOT
#ADD stark-oauth-microservice.war ./webapps/ROOT.war
ADD ./target/stark-oauth-microservice.war ./webapps/ROOT.war
ADD ./src/main/resources/application.properties /usr/local/tomcat/
#RUN ["apt-get", "update"]
#RUN ["apt-get", "install", "-y", "nano"]
EXPOSE 8080
ENTRYPOINT ["catalina.sh", "run"]

