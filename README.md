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

### Database Management

#### Initialising
When docker-compose up is run, the `postgres/init/init_db.sql` file in the repo is copied over to the docker container, which is then 
run. Another option to doing this process manually is to:

- Copy over the init.db.sql file from this repo into the root of the docker container: `docker cp init_db.sql fanyacodedb:/`
- Exec into the container with bash: `docker container exec -it fanyacodedb bash`
- Run init_db.sql file postgres user: `psql -U postgres --file init_db.sql`

#### Persisting data
For local development, data is persisted locally in the `postgres/postgres-local-data` dir. This data is reloaded even when
the docker container is restarted. The downside for this is if the schemas in `init_db.sql` are updated, the date loading
on start up may have some issues due to mismatch. To deal with this, we shall introduce `flyway` migration to perform schema
migrations on start up without having to update the `init_db.sql` file. 

Optional: Data from another data may be loaded into the running by running sql dumps. To this, comment out the `/postgres-local-data`
section in the `docker-compose` file. 
- To take a dump, run```docker exec -t fanyacodedb pg_dumpall -c -U postgres > dump_`date +%d-%m-%Y"_"%H_%M_%S`.sql```
- To restore data from a dump, run `cat your_dump.sql | docker exec -i fanyacodedb psql -U postgres`
*Guide adopted from [here](https://medium.com/codex/how-to-persist-and-backup-data-of-a-postgresql-docker-container-9fe269ff4334#id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6IjE1NDllMGFlZjU3NGQxYzdiZGQxMzZjMjAyYjhkMjkwNTgwYjE2NWMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2NTkyOTExNjcsImF1ZCI6IjIxNjI5NjAzNTgzNC1rMWs2cWUwNjBzMnRwMmEyamFtNGxqZGNtczAwc3R0Zy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwNDQ0NzY5NTM3ODMxMjA0MTQ5NiIsImVtYWlsIjoicG93ZXJtdWtpc2FAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF6cCI6IjIxNjI5NjAzNTgzNC1rMWs2cWUwNjBzMnRwMmEyamFtNGxqZGNtczAwc3R0Zy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsIm5hbWUiOiJQb3dlciBNdWtpc2EiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EtL0FGZFp1Y3JyWmNfVmJZMVFRN0xmR1lQWEdZQnAzRUViRm1DQmtsSWlJeW92YTVrPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IlBvd2VyIiwiZmFtaWx5X25hbWUiOiJNdWtpc2EiLCJpYXQiOjE2NTkyOTE0NjcsImV4cCI6MTY1OTI5NTA2NywianRpIjoiZGM2ODc1YWFjMDZmOTU4YmExY2ZhMzZjMWVhZDNjNWE3ZTZhMjhlZCJ9.DCORiknTKmpnYxozeRC9VxNzMNfZlRZzdvoGQA0qSK220QqbfD_ql3F7gruBmaSxNG78a89Ls5ROg0WsS9OYWI7h96F7fmUANYd7MC2bXR-mXTcP4DJ0Qzga0eTZgh8LDithAyfDVRK8pHgd7lMm4H6acV0xwLhzKqfvFa0uKPxUM1oWWFbrkP5ISkV9uojnvJ_3fynQBEnnV04UpunaJGKDK3WfMQkfw4vEw0tFdr9PDGILW5s-T2VqGk4aH87Lm0WJyrkIoQIMrDcHd25KT7G3CFUsMg8Pc1PJLL6AOTvIplVfsxoMv2XXr_wAurJkmEa9K7idPKCEXKFdR9w6aQ)

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

## Deploy to DockerHub
Dockerhub is good for sharing images with teammates.
- Create jar file: `./gradlew build -x test`
- Build docker image from DockerFile: `docker build -t fanyacode-app .`
- Run `docker images` to list images
- Login to docker `docker login --username=powermukisa`
- Tag the image: `docker tag [imageId] powermukisa/fanyacode-app:latest`
- Push image: `docker push powermukisa/fanyacode-app:latest`
