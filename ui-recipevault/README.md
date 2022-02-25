# Recipe Vault

## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
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


### Local Testing


docker run -d --name nginx -p 8080:8080 -v /Users/geoallen/Developer/recipevault/dist:/opt/app-root/src:Z registry.access.redhat.com/ubi8/nginx-118 nginx -g "daemon off;"

docker run -d --name nginx -p 8081:8081 -v /Users/geoallen/Developer/recipevault/dist:/opt/app-root/src:Z registry.access.redhat.com/ubi8/nginx-118 nginx -g "daemon off;"


docker run -d --name recipevault-frontend -p 8080:8080 localhost/recipevault-frontend nginx -g "daemon off;"

podman run -d --name recipevault-frontend -p 8080:8080 localhost/recipevault-frontend nginx -g "daemon off;"


### Deploy to OpenShift

Use S2I - WIP

## Production Build

## Obtain RecipeVault Backend Endpoint

oc get services 

yarn build or npm run build

This outputs to /dist

## Build Container Image

podman build  -t quay.io/geoallen/recipevault-frontend:stage -f Containerfile .

podman push quay.io/geoallen/recipevault-frontend:stage

## Deploy Image to OCP

oc new-app --docker-image=quay.io/geoallen/recipevault-frontend --name=recipevault-frontend

oc expose service/recipevault-frontend

oc get route


oc set volume deployment/recipevault-frontend --add --name=recipevault-pvc -t pvc --claim-name=recipevault-pvc --mount-path=/opt/app-root/src/images



## Update App











podman build  -t recipevault-frontend -f Containerfile .

podman build  -t quay.io/geoallen/recipevault-frontend -f Containerfile .


podman push quay.io/geoallen/recipevault-frontend

