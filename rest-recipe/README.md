# RecipeVault:Quarkus

 - RESTEasy to expose the REST endpoints
 - Hibernate ORM with Panache to perform the CRUD operations on the database
 - A PostgreSQL database - (Uses Dev Services for local testing)
 - Image storage with S3 compatible storage

## Requirements

To compile and run this demo you will need:

- JDK 11+
- GraalVM (if you want to compile to native)

# Development Guide

## With Local S3 Storage 

# S3 local instance
# start local S3 emulator
`podman run --rm --name local-s3 -p 4566:4566 -p 4572:4572 -e SERVICES=s3 -e START_WEB=0 -d localstack/localstack`

# Create bucket
`aws s3 mb s3://geoallen.recipevault.dev.images --endpoint-url=http://localhost:4566`
make_bucket: geoallen.recipevault.dev.images

# Confirm 'recipe-images' Bucket is listed
`aws s3 ls --endpoint-url=http://localhost:4566`
2022-01-14 10:01:21 geoallen.recipevault.dev.images

I was unable to resolve issues with aws s3 cli and Self-Signed Certs.  I was able to use s3cmd successfully. - https://github.com/s3tools/s3cmd

# s3 Configuration on ODF with s3cmd
s3cmd --configure

S3 Endpoint (Don't include protocol prefix): s3-openshift-storage.apps.ocp.webwim.com

DNS-style bucket+hostname:port template for accessing a bucket: s3-openshift-storage.apps.ocp.webwim.com/%(bucket)

For self-signed certs ensure Use HTTPS protocol: False

# Modify Bucket Policy to Anonymous Read - Then the recipe images can be served from S3.

Update public_s3.json with your bucket name

s3cmd setpolicy public_s3.json s3://geoallen.recipevault.dev.i-ad7cc188-3bc4-4f91-b413-3b802e7511e6

# Put a test object/image
s3cmd put ./rest-recipe/src/test/resources/payloads/images/blueberry_kuechen.jpg s3://demo-bucket-6dcbeaba-6922-4244-bdb5-8c0e6d8a220b/bluebery.jpg

# Validate the URL
https://s3-openshift-storage.apps.ocp.webwim.com/geoallen.recipevault.dev.i-ad7cc188-3bc4-4f91-b413-3b802e7511e6/1f92d7ca-94ae-45bc-bce2-4fadf7885d5e.jpeg


## PostgreSQL
Next we need to make sure you have a PostgreSQL instance running (Quarkus automatically starts one for dev and test mode). To set up a PostgreSQL database with Docker:

> docker run -it --rm=true --name quarkus_test -e POSTGRES_USER=quarkus_test -e POSTGRES_PASSWORD=quarkus_test -e POSTGRES_DB=quarkus_test -p 5432:5432 postgres:13.3

Connection properties for the Agroal datasource are defined in the standard Quarkus configuration file,
`src/main/resources/application.properties`.

### Live coding with Quarkus

The Maven Quarkus plugin provides a development mode that supports
live coding. To try this out:

> cd rest-recipe
> ./mvnw quarkus:dev

In this mode you can make changes to the code and have the changes immediately applied, by just refreshing your browser.

    Hot reload works even when modifying your JPA entities.
    Try it! Even the database schema will be updated on the fly.


# Run Tests

## Build Jar
Build the Jar to deploy on OCP
mvn clean package -DskipTests -Dquarkus.package.type=uber-jar

Package for building image
./mvnw -DskipTests=true package

# Build a container image:

podman build -f src/main/docker/Dockerfile.jvm -t rest-recipe:1.0 .

podman tag rest-recipe:1.1 quay.io/geoallen/rest-recipe:1.1

podman login quay.io

podman push quay.io/geoallen/rest-recipe:1.1

## Postgres

oc new-app postgresql-persistent \
-p POSTGRESQL_USER=recipevault -p POSTGRESQL_PASSWORD=recipevault -p POSTGRESQL_DATABASE=recipevaultdb

# Using S3 AWS account

Before you can use the AWS SDKs with S3, you must get an AWS access key ID and secret access key. 
For more information, see:
 - [Sign up for AWS and Create an IAM User](https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/signup-create-iam-user.html)
 - [Set Up AWS Credentials and Region for Development](https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/setup-credentials.html)

Create a S3 bucket using AWS CLI using your default AWS profile
`aws s3 mb s3://quarkus.s3.quickstart.11.22.33`

**NOTE: Please assure the bucket name you created is unique across AWS S3. 
See [Amazon S3 Bucket Naming Requirements](https://docs.aws.amazon.com/AmazonS3/latest/dev/BucketRestrictions.html#bucketnamingrules)**


# Running in native
You can compile the application into a native executable using:

`./mvnw clean package -Pnative`

and run with:

`./target/` 
