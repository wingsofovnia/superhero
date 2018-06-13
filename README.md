# Superhero [![Build Status](https://travis-ci.org/wingsofovnia/superhero.svg?branch=master)](https://travis-ci.org/wingsofovnia/superhero)

A simple REST app based on reactive Spring Data MongoDB repositories and Spring 5 WebFlux endpoints.

Available at: [https://superhero-api.herokuapp.com/](https://payworks-superhero-api.herokuapp.com/) (admin:nimda).
Postman collection: [https://www.getpostman.com/collections/1c2897de9283ee9cdf82](https://www.getpostman.com/collections/1c2897de9283ee9cdf82).

## System requirements
The application requires JRE 1.8 to installed. To run the application in standalone mode without a virtual environment, a connection to the MongoDB is needed. Docker and `docker-compose` are required for running a containerized version of the application. 

### Building the project
To build the project use
```
$ ./gradlew assemble
```
this creates a runnable java available in `build/libs`.

The `stage` task is required by Dockerfile and Heroku deployment mechanism as they expect `app.jar` artifact in `build/libs` folder.
```
$ ./gradlew stage
``` 

### Deployment
#### Locally
If you have a local MongoDB instance running, you may want to run the application without virtual environment:
```
$ ./gradlew bootRun
```
This runs the application that connects to localhost:27017 MongoDB instance. The application endpoints will be available at: [http://localhost:8080/api/superheroes](http://localhost:8080/api/superheroes).

#### Docker
To run a containerized version of the application you can use the following command:
```
$ ./gradlew clean stage && docker-compose up (--build --force-recreate)
```
This runs a MongoDB and the application containers. The application endpoints will be available at: [http://localhost:8080/api/superheroes](http://localhost:8080/api/superheroes).

#### Heroku
The distribution contains a Heroku deployment descriptor and is ready to be deployed to the cloud with the `prod` profile activated. It is required that the following environment variables are available:
```
DB_HOST - MongoDB host
DB_NAME - MongoDB database name
DB_USERNAME - MongoDB username
DB_PASSWORD - MongoDB user password
USR_NAME - username for accessing API endpoints
USR_PASSWORD - username password for accessing API endpoints
```

## Bugs and Feedback
For bugs, questions and discussions please use the [Github Issues](https://github.com/wingsofovnia/superhero/issues).

## License
Except as otherwise noted this software is licensed under the [MIT](https://opensource.org/licenses/MIT).

Licensed under the MIT (the "License"); you may not use this file except in compliance with the License.
