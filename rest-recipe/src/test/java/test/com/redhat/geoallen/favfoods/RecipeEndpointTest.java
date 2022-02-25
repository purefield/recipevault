package test.com.redhat.geoallen.favfoods;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;

import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.response.*;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class RecipeEndpointTest {
	
	

 
	@Test
	public void createRecipe() {

		String userDirectory = Paths.get("")
			.toAbsolutePath()
			.toString();

		System.out.println("Directory: " + userDirectory);
		Recipe recipe = getTestRecipe();
		

		Response response =
		
	given()
	.baseUri("http://localhost:8080/recipes")
	.contentType(ContentType.MULTIPART)
        .multiPart("file", new File("./src/test/resources/payloads/images/blueberry_kuechen.jpg"))
		.multiPart("filename", "blueberry_kuechen.jpg")
		.multiPart("mimetype","image/jpeg")
        .multiPart("recipe", recipe, "application/json")
        
		// WHEN
	.when()
		.post()
		.then()
		.assertThat()
		.statusCode(201)
		.log()
		.all(true)
		.extract()
		.response();
	

	}

	@Test
	public void createUpdateRecipe() {

		String userDirectory = Paths.get("")
			.toAbsolutePath()
			.toString();

		System.out.println("Directory: " + userDirectory);

		Recipe recipe = getTestRecipe();
	
		Response response =
		
	given()
	.baseUri("http://localhost:8080/recipes")
	.contentType(ContentType.MULTIPART)
        .multiPart("file", new File("./src/test/resources/payloads/images/blueberry_kuechen_update.jpg"))
		.multiPart("filename", "blueberry_kuechen_update.jpg")
		.multiPart("mimetype","image/jpeg")
        .multiPart("recipe", recipe, "application/json")
        
		// WHEN
	.when()
		.post()
		.then()
		.assertThat()
		.statusCode(201)
		.log()
		.all(true)
		.extract()
		.response();
		Integer id = response.path("id");
		recipe.title = "Recipe Title-Update";
		given()
				.multiPart("recipe", recipe, "application/json")
				.when().put("http://localhost:8080/recipes/{id}", id)
						.then()
						.statusCode(201);
		
		given()
				.when().get("http://localhost:8080/recipes/{id}", id)
				.then()
				.contentType(ContentType.JSON)
				.body("title", equalTo("Recipe Title-Update"));

	}


	@Test
	public void createUpdateDeleteRecipe() {


		Recipe recipe = getTestRecipe();
		

		Response response =
		
	given()
	.baseUri("http://localhost:8080/recipes")
	.contentType(ContentType.MULTIPART)
        .multiPart("file", new File("./src/test/resources/payloads/images/blueberry_kuechen_delete.jpg"))
		.multiPart("filename", "blueberry_kuechen_delete.jpg")
		.multiPart("mimetype","image/jpeg")
        .multiPart("recipe", recipe, "application/json")
        
		// WHEN
	.when()
		.post()
		.then()
		.assertThat()
		.statusCode(201)
		.log()
		.all(true)
		.extract()
		.response();
		Integer id = response.path("id");
		recipe.title = "Recipe Title-Update";
		given()
				.multiPart("recipe", recipe, "application/json")
				.when().put("http://localhost:8080/recipes/{id}", id)
						.then()
						.statusCode(201);
		
		given()
				.when().get("http://localhost:8080/recipes/{id}", id)
				.then()
				.contentType(ContentType.JSON)
				.body("title", equalTo("Recipe Title-Update"));
		given()
				.when().delete("http://localhost:8080/recipes/{id}", id)
				.then()
				.statusCode(204);

	}


	  /*  @Test
	public void recipePost()
	{
		// Creating a File instance 
		File jsonDataInFile = new File("/Users/geoallen/Developer/openeats-quarkus/src/test/resources/payloads/full_recipe.json");
		
		Response response =
		//GIVEN
		    given()
				.baseUri("http://localhost:8080/recipes")
				.contentType(ContentType.JSON)
				.body(jsonDataInFile)
		// WHEN
			.when()
				.post()
			.then()
				.assertThat()
				.statusCode(201)
				.log()
				.body()
				.extract().
        		response();
			Integer id = response.path("id");
			given()
			.when().delete("http://localhost:8080/recipes/{id}", id)
					.then()
					.statusCode(204);

	} */



	public Recipe getTestRecipe()  {

		

		List<String> ingredients = new ArrayList();
		ingredients.add("ing0");
		ingredients.add("ing1");
		ingredients.add("ing2");

	
		Recipe recipe = new Recipe();
		recipe.title = "title";
		recipe.description = "description";
		recipe.author="Pat Smith";
		recipe.cook_time = 60;
		recipe.course ="Breakfast";
		recipe.cuisine = "American";
		recipe.directions = "Mix, Stir, Bake";

		recipe.image_name = "test-filename.jpg";
		
		recipe.prep_time =30;
		recipe.servings = 8;
		recipe.serving_unit = "Pieces";

		recipe.source = "All Recipes or Grammy's Recipe Book";
		recipe.tags = "tag1, tag2, tag3";
		recipe.userid = "user123";


		recipe.ingredients = ingredients;

		return recipe;



	}







}
