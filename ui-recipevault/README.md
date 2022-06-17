# # RecipeVault:VueJS

 - Vue.js 3 UI for RecipeVault

## Project setup
```
yarn install
```
### Compiles and hot-reloads for development

Environment variables for the UI are defined in ([env-config.js](./public/env-config.js)).  

While typically vue.js environment variables are defined in .env files.  This configuration allows the ability to define ENV varibles at runtime so they can be modified during deployment.

```
yarn serve
```

### Compiles and minifies for production
```
yarn build
```

### Lints and fixes files
```
yarn lint
```

### Deploy to OpenShift

## Obtain RecipeVault Backend Endpoint

oc get services 

yarn build or npm run build

This outputs to /dist

## Build Container Image

podman build  -t ui-recipevault -f Containerfile .

podman build  -t quay.io/geoallen/ui-recipevault:1.0 -f Containerfile .

podman tag  ui-recipevault quay.io/geoallen/ui-recipevault:1.1

podman push quay.io/geoallen/ui-recipevault:1.0

## Deploy Image to OCP

Obtain the route the Recipe REST service 
oc get route 

Obtain the S3 Endpoint - 

Define Configmap with following values:

oc create configmap recipe-ui --from-literal=key1=config1 --from-literal=key2=config2

oc create configmap recipe-ui --from-literal=VUE_APP_IMAGE_SERVER_URL=https://s3.us-east-2.amazonaws.com/recipevault.dev.images/ --from-literal=VUE_APP_RECIPE_DATA_SERVICE=http://localhost:8080/

VUE_APP_IMAGE_SERVER_URL="https://s3.us-east-2.amazonaws.com/recipevault.dev.images/"
VUE_APP_RECIPE_DATA_SERVICE="http://localhost:8080/"

oc new-app --name=recipevault-frontend --docker-image=quay.io/geoallen/recipevault-frontend 

oc set env --from=configmap/recipe-ui  dc/recipevault-frontend

oc expose service/recipevault-frontend

oc get route

## Ignorable Errors ##

Uncaught TypeError: Cannot read properties of undefined (reading 'globalProperties')





