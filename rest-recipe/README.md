# RecipeVault:Quarkus

 - RESTEasy to expose the REST endpoints
 - Hibernate ORM with Panache to perform the CRUD operations on the database
 - A PostgreSQL database; see below to run one via Docker
 - Image storage with S3 compatible storage

## Requirements

To compile and run this demo you will need:

- JDK 11+
- GraalVM

# Development Guide

## With Local S3 Storage ([S3 Deeper Dive](./S3-README.md))
# S3 local instance
# start local S3 emulator
`podman run --rm --name local-s3 -p 4566:4566 -p 4572:4572 -e SERVICES=s3 -e START_WEB=0 -d localstack/localstack`

# Create bucket
`aws s3 mb s3://geoallen.recipevault.dev.images --endpoint-url=http://localhost:4566`
make_bucket: geoallen.recipevault.dev.images

# Confirm 'recipe-images' Bucket is listed
`aws s3 ls --endpoint-url=http://localhost:4566`
2022-01-14 10:01:21 geoallen.recipevault.dev.images


## PostgreSQL
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

# Run dev mode
- Run `./mvnw clean package` and then `java -jar ./target/quarkus-app/quarkus-run.jar`
- In dev mode `./mvnw clean quarkus:dev`

# Running in native
You can compile the application into a native executable using:

`./mvnw clean package -Pnative`

and run with:

`./target/` 


### Build and Deploy to OpenShift

## REST Service

Using project "recipevault-dev" on server "https://api.cluster-p889x.p889x.sandbox741.opentlc.com:6443".
geoallen1-mac:recipevault-backend geoallen$ ./mvnw clean package -DskipTests -Dquarkus.kubernetes.deploy=true

Build the Jar
mvn clean package -DskipTests -Dquarkus.package.type=uber-jar

Build a docker image:
`docker build -f src/main/docker/Dockerfile.jvm -t quay.io/geoallen/rest-recipe .`

mvn clean package -DskipTests -Dquarkus.package.type=uber-jar


## Postgres

oc new-app postgresql-persistent \
-p POSTGRESQL_USER=recipevault -p POSTGRESQL_PASSWORD=recipevault -p POSTGRESQL_DATABASE=recipevaultdb

Next we need to make sure you have a PostgreSQL instance running (Quarkus automatically starts one for dev and test mode). To set up a PostgreSQL database with Docker:


## Recipe Vault 

curl -X "DELETE" http://localhost:8080/recipes/2