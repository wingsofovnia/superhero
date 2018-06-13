FROM openjdk:8-alpine

RUN apk update && apk add bash

RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

COPY build/libs/app.jar $PROJECT_HOME/app.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-Dspring.data.mongodb.uri=mongodb://mongo/superhero?retryWrites=true","-Djava.security.egd=file:/dev/./urandom","-jar","./app.jar"]
