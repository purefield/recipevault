# RecipeVault

Recipe Vault is a Quarkus and Vue.js based application Recipe Manager

# RecipeVault - Tools Used

 - RESTEasy to expose the REST endpoints
 - Hibernate ORM with Panache to perform the CRUD operations on the database
 - A PostgreSQL database - (Uses Dev Services for local testing)
 - Image storage with S3 compatible storage 
 - S3 CMD - https://github.com/s3tools/s3cmd
 - Docker Desktop to support Test Containers/Quarkus Dev Services
 - Podman - Managed images and test locally. 
 - jq - commandline JSON processor [version 1.6]


## Deploy to OCP 

[Deploy to OCP ](./deploy/README.md)


## Run demo locally

### Configure and startup the REST Service
[REST-Recipe Service](./rest-recipe/README.md)

### Configure and Startup the UI
[UI-RecipeVault](./ui-recipevault/README.md)




