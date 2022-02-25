package com.redhat.geoallen.resource;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;

import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.redhat.geoallen.view.RecipeDTO;
import com.redhat.geoallen.view.RecipeFormData;
import com.redhat.geoallen.orm.panache.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.panache.common.Sort;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("recipes")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class RecipeResource extends CommonResource {
    @Inject
    S3Client s3;

    @Inject
   Logger log; 

   @ConfigProperty(name = "image.persist.strategy")
    String persistStrategy;

    

    @GET
    public List<Recipe> get() {
        return Recipe.listAll(Sort.by("title"));
        
    }

    @GET
    @Path("/userid/{userid}")
    public List<Recipe> getByUserId(@PathParam String userid) {  
        return Recipe.findByUserID(userid);
    }

    @GET
    @Path("{id}")
    public Recipe getSingle(@PathParam Long id) {
        Recipe entity = Recipe.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Recipe with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

     @POST
    @Transactional
    public Response create(Recipe Recipe) {
        if (Recipe.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        Recipe.persist();
        return Response.ok(Recipe).status(201).build();
    } 

    @POST
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@MultipartForm RecipeFormData formData) throws Exception {

        //log.info(formData.title);
        log.info(formData.fileName);
        log.info(formData.mimeType);
        log.info(formData.recipe);

        
                //return Response.ok().status(Status.OK).build();
                if (formData.fileName == null || formData.fileName.isEmpty()) {
                    return Response.status(Status.BAD_REQUEST).build();
                }

                
                if (formData.mimeType == null || formData.mimeType.isEmpty()) {
                    return Response.status(Status.BAD_REQUEST).build();
                }
                
                if (persistStrategy.equals("S3")) {
                PutObjectResponse putResponse = s3.putObject(buildPutRequest(formData),
                        RequestBody.fromFile(uploadToTemp(formData.data)));
                }

                if (persistStrategy.equals("LOCAL")) {
                    saveLocal(formData.data,formData.fileName, formData.mimeType);

                }

                //if (putResponse != null) {
                    // if the S3 completed successfully, use the url in the recipe info
                    //Should we add S3 URL location to the record or just calcuate URL?
                    //http://recipe-images.localhost:4566/recipe-images/Fuse_79_Components.png
                
                
    
            
            //Recipe newRecipe =  new Recipe(formData);

            String recipeJSON = formData.recipe;
            ObjectMapper objectMapper = new ObjectMapper();
            RecipeDTO recipeDTO = objectMapper.readValue(recipeJSON, RecipeDTO.class);

            log.info("From DTO: " + recipeDTO.title);

            Recipe newDTORecipe = new Recipe(recipeDTO, formData.fileName);

            newDTORecipe.persist();

            return Response.ok(newDTORecipe).status(Status.CREATED).build();
        
        /** TODO - Need to make this a try - catch
        else {
            return Response.serverError().build();
        } 
        */
    }


    @PUT
    @Transactional
    @Path("/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateRecipe(@PathParam Long id, @MultipartForm RecipeFormData updatedRecipe) throws Exception {

        log.info("id: " +  id);
        

        //need to find by ID 
        Recipe existingRecipe= Recipe.findById(id);

        if (existingRecipe == null) {
            throw new WebApplicationException("Recipe with id of " + id + " does not exist.", 404);
        }

        String recipeJSON = updatedRecipe.recipe;
        ObjectMapper objectMapper = new ObjectMapper();
        RecipeDTO recipeDTO = objectMapper.readValue(recipeJSON, RecipeDTO.class);

        log.info("From DTO: " + recipeDTO.title);

        //log.info(updatedRecipe.title);
        //log.info(updatedRecipe.fileName);

        /*Map<String, Object> parameters = new HashMap<>();
        addIfNotNull(parameters, "title", updatedRecipe.title );
        //addIfNotNull(parameters, "id", String.valueOf(id) );
         addIfNotNull(parameters, "status", status );
        addIfNotNull(parameters, "email", email ); */


        /*String query = parameters.entrySet().stream()
            .map( entry -> entry.getKey() + "=:" + entry.getKey() )
            .collect( Collectors.joining(" and ") );*/

        
        existingRecipe.title = recipeDTO.title;
        existingRecipe.description= recipeDTO.description;

        /** 
         if (updatedRecipe.fileName == null || updatedRecipe.fileName.isEmpty()) {
            return Response.status(Status.BAD_REQUEST).build();
        }
 
        if (updatedRecipe.mimeType == null || updatedRecipe.mimeType.isEmpty()) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        PutObjectResponse putResponse = s3.putObject(buildPutRequest(updatedRecipe),
                RequestBody.fromFile(uploadToTemp(updatedRecipe.data)));
        if (putResponse != null) {
            // if the S3 completed successfully, use the url in the recipe info
            //Should we add S3 URL location to the record or just calcuate URL?
            //http://recipe-images.localhost:4566/recipe-images/Fuse_79_Components.png
            
            
            existingRecipe.persist();
            */

            return Response.ok().status(Status.CREATED).build();
        /** } else {
            return Response.serverError().build();
        } */
    }



    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Long id) {
        Recipe entity = Recipe.findById(id);
        DeleteObjectResponse deleteResponse = null;
        boolean fileDeleted = false;


        if (entity == null) {
            throw new WebApplicationException("Recipe with id of " + id + " does not exist.", 404);
        }

        // if using S3
        if (entity.image_name != null && persistStrategy.equals("S3")) {
        deleteResponse = s3.deleteObject(buildDeleteRequest(entity.image_name));
        log.info(deleteResponse.toString());
        }

        // if using file storage
        if (entity.image_name != null && persistStrategy.equals("LOCAL")) {
        fileDeleted = deleteLocal(entity.image_name);
            }

        // if either were successfull, delete the DB entity
        if (deleteResponse != null || fileDeleted) {
        entity.delete(); 
        }
    return Response.status(204).build();
}

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            //log.error("Failed to handle request", exception);

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", exception.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }

    private static void addIfNotNull(Map<String, Object> map, String key, String value) {
        if (value != null) {
            map.put(key, value);
        } 


}
}
