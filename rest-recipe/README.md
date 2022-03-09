# RecipeVault:Quarkus

 - RESTEasy to expose the REST endpoints
 - Hibernate ORM with Panache to perform the CRUD operations on the database
 - A PostgreSQL database; see below to run one via Docker
 - Image with either local PV or S3 compatible storage

## Requirements

To compile and run this demo you will need:

- JDK 11+
- GraalVM

Development 

## Local Storage


## With S3 Storage
[S3 Configuration](./S3-README.md)


## PostgreSQL

oc new-app postgresql-persistent \
-p POSTGRESQL_USER=recipevault -p POSTGRESQL_PASSWORD=recipevault -p POSTGRESQL_DATABASE=recipevaultdb


Next we need to make sure you have a PostgreSQL instance running (Quarkus automatically starts one for dev and test mode). To set up a PostgreSQL database with Docker:

> docker run -it --rm=true --name quarkus_test -e POSTGRES_USER=quarkus_test -e POSTGRES_PASSWORD=quarkus_test -e POSTGRES_DB=quarkus_test -p 5432:5432 postgres:13.3

Connection properties for the Agroal datasource are defined in the standard Quarkus configuration file,
`src/main/resources/application.properties`.

### Live coding with Quarkus

The Maven Quarkus plugin provides a development mode that supports
live coding. To try this out:

> ./mvnw quarkus:dev

In this mode you can make changes to the code and have the changes immediately applied, by just refreshing your browser.

    Hot reload works even when modifying your JPA entities.
    Try it! Even the database schema will be updated on the fly.


### Build and Deploy to OpenShift


Using project "recipevault-dev" on server "https://api.cluster-p889x.p889x.sandbox741.opentlc.com:6443".
geoallen1-mac:recipevault-backend geoallen$ ./mvnw clean package -DskipTests -Dquarkus.kubernetes.deploy=true

Build the Jar
mvn clean package -DskipTests -Dquarkus.package.type=uber-jar

Build a docker image:
`docker build -f src/main/docker/Dockerfile.jvm -t quay.io/geoallen/rest-recipe .`

mvn clean package -DskipTests -Dquarkus.package.type=uber-jar


curl -X "DELETE" http://localhost:8080/recipes/2

# Run the demo in dev mode

- Run `./mvnw clean package` and then `java -jar ./target/quarkus-app/quarkus-run.jar`
- In dev mode `./mvnw clean quarkus:dev`

# Running in native

You can compile the application into a native executable using:

`./mvnw clean package -Pnative`

and run with:

`./target/` 

# Running native in container (using localstack)

