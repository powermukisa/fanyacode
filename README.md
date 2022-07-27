# FanyaCode : Personalised Algorithm sharing

## Description

FanyaCode allows Software Engineers to quickly create their own algorithms and unit tests, for future reference.
With FanyCode, engineers can practice algorithms and revist the site when studying, preparing for interviews, or just doing
research to see which algorithm works best for a given task.


## Requirements

* [OpenJDK 11](https://adoptopenjdk.net)
* [Gradle](https://gradle.org/) ([Why gradle?](https://gradle.org/maven-vs-gradle/))

## Run on local (using Intellij)

### Run postgres
- Comment out fanyacode service in docker-compose.yml file
- Run `docker-compose down`
- Run `docker-compose up`

Troubleshooting: If docker compose fails because a port is already occupied,  list ports to check which ports could be already occupied:
`sudo lsof -i tcp:<port_number>`
`kill -9 <process_id>`

### Initialise db
Everytime the Postgres docker container is restarted, an empty database will be created. To set up the database (set up tables, sequences, etc),:

- Copy over the init.db.sql file from this repo into the root of the docker container: `docker cp init_db.sql fanyacodedb:/`
- Exec into the container with bash: `docker container exec -it fanyacodedb bash`
- Run init_db.sql file postgres user: `psql -U postgres --file init_db.sql`

### Run app
Once Postgres is ready in a docker container,  run the app via the Intellij Run interface. A default run config should be created for 
the main FanyacodeApplication class. When the app is running, observe the logs. You should see something like 
"Tomcat started on port(s): 8080 (http) with context path". That's an indication that the service has started.

## Run on local (using docker entirely)
- Generate the jar file: ` ./gradlew clean build -x test`
- Run `docker-compose down`
- Run `docker-compose up`

## Connection to the database
Since we are using hibernate's create ddl-atuo mode (in applications.properties), Spring will auto create tables in the
fanyacode (In this case we only have the users table so far).

### GUI
The easiest way it to visualise the database through tools like (DBeaver)[https://dbeaver.com/]
After connection with username and password as `postgres` you should see an empty user's table.
Connection details: [!](src/main/resources/postgresl-readme-screenshot.png)


### Terminal
- To directly launch the psql shell run this instead: `docker exec -tiu postgres fanyacodedb psql`

To connect to the container itself but not launch psql shell:
- Run `docker ps` to list docker containers
- Run `docker exec -it <container_id> sh` to connect to postgres container

To exit psql shell: `exit`

Psql quick commands:
- Show dbs : `\l;`
- Connect to a db: `\c users;`
- Show data in column: `SELECT * FROM users.id;`
- Clear column: `truncate table users.id;`
- Create db: `create database other_users;`

## Testing
Run unit tests with `./gradlew test`


## Documentation
- Swagger: http://localhost:8444/swagger-ui/index.html

## Postman 
The Fanya Postman Workspace has a collection of the endpoints in this repo - for ease of e2e testing

## Intellij Http Client
Instead of Postman, you can also run requests via the `test.http` file

## Architecture
Initial set with postgres, Spring jdbc and JWT auth : https://www.youtube.com/watch?v=5VUjP1wMqoE&t=1s
