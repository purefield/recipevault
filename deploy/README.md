# RecipeVault

Recipe Vault is a Quarkus and Vue.js based Recipe Manager application

# RecipeVault - Requirements

 - RESTEasy to expose the REST endpoints
 - Hibernate ORM with Panache to perform the CRUD operations on the database
 - A PostgreSQL database - (Uses Dev Services for local testing)
 - Image storage with S3 compatible storage 
 - Docker Desktop (I'm on a mac) to support Test Containers/Quarkus Dev Services
 -


## Run on Openshift

# Deploy Postgres DB - There are multiple options. Here is the command to use the Persistent Template on OCP.

`oc new-app postgresql-persistent \
-p POSTGRESQL_USER=recipevault -p POSTGRESQL_PASSWORD=recipevault -p POSTGRESQL_DATABASE=recipevaultdb`

# Provision S3 compatible Bucket and ensure objects are Read only for Anonymous. 

# Modify Bucket Policy to Anonymous Read - Then the recipe images can be served from S3.

Update [s3 Policy](./s3/public_s3.json) with your bucket name

Using s3 command line tool of choice, apply the policy.

`s3cmd setpolicy public_s3.json s3://<BUCKET_NAME>`

Update [s3 Secret](./s3/s3-recipevault-secret.yaml) ACCESS KEY ID and SECRET

Deploy s3 secret

`oc apply -f ./s3/s3-recipevault-secret.yaml`

# Deploy OCP Resources

Login to OCP and create or select project

Update [rest-recipe-config.yaml](./rest-recipe/rest-recipe-config.yaml) with Bucket name and connection details

Deploy Recipe Rest Service and relatedd resources

`oc apply -f ./rest-recipe/ . `

Obtain the route 

`oc get route' and update [recipevault-ui-config.yaml](./ui-recipevault/recipevault-ui-config.yaml) with REST Service endpoint and S3 Bucket Endpoint

Deploy UI 

Apply UI resources to OCP
`oc apply -f ./ui-recipevault/ .`

`oc get route ui-recipe`

Access the UI and create recipes!!


