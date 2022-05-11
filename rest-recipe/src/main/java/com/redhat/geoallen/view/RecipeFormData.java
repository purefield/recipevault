package com.redhat.geoallen.view;

import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;



public class RecipeFormData {

    

    @FormParam("title")
    @PartType(MediaType.TEXT_PLAIN)
    public String title;

    // need to use the userid from the authenicated token
    //@FormParam("")
    //public String userid;

    //public String author;

    //photo 
    //photo_thumbnail 

    @FormParam("cuisine")
    @PartType(MediaType.TEXT_PLAIN)
    public String cuisine;

    @FormParam("course")
    @PartType(MediaType.TEXT_PLAIN)
    public String course;

    @FormParam("tags")
    @PartType(MediaType.TEXT_PLAIN)
    public String tags;

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;

    @FormParam("filename")
    @PartType(MediaType.TEXT_PLAIN)
    public String filename;

    @FormParam("mimetype")
    @PartType(MediaType.TEXT_PLAIN)
    public String mimetype;

    //@FormParam("ingredients")
    //public List<Ingredient> ingredients = new ArrayList<Ingredient>();
    
    @FormParam("description")
    @PartType(MediaType.TEXT_PLAIN)
    public String description;

    @FormParam("ingredients")
    @PartType(MediaType.TEXT_PLAIN)
    public String ingredients;

    @FormParam("directions")
    @PartType(MediaType.TEXT_PLAIN)
    public String directions;

    @FormParam("source")
    @PartType(MediaType.TEXT_PLAIN)
    public String source;

    @FormParam("prep_time")
    @PartType(MediaType.TEXT_PLAIN)
    public String prep_time;

    @FormParam("cook_time")
    @PartType(MediaType.TEXT_PLAIN)
    public String cook_time;

    @FormParam("servings")
    @PartType(MediaType.TEXT_PLAIN)
    public String servings;

    @FormParam("recipe")
    @PartType(MediaType.APPLICATION_JSON)
    public String recipe;
    

    

}
