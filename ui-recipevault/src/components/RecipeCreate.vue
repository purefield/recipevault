<template>


<h2> Create New Recipe </h2>

<div v-if="!submitted">

<div class="mb-3">
 <div class="form-group">
        <label for="title">Title</label>
        <input
          type="text"
          class="form-control"
          id="title"
          required
          v-model="recipe.title"
          name="title"
        />
      </div>
</div>

<div class="mb-3">
  <label for="formFile" class="form-label">Recipe Image</label>
  <input type="file" ref="file" class="form-control" @change="selectFile" />
</div>

<div class="mb-3">
  <div id="preview">
    <img v-if="url" :src="url" />
  </div>
</div>


<div class="mb-3">
      <div class="form-group">
        <label for="description">Description</label>
        <textarea
          class="form-control"
          id="info"
          required
          v-model="recipe.description"
          name="info"
        />
      </div>
</div>

<div class="mb-3">
      <div class="form-group">
        <label for="ingredients">Ingredients - 1 per line</label>
        <textarea
          class="form-control"
          id="info"
          required
          v-model="recipe.ingredient_list"
          name="ingredients"
          rows="7"
        />
      </div>
</div>

<div class="mb-3">
      <div class="form-group">
        <label for="directions">Directions</label>
        <textarea
          class="form-control"
          id="directions"
          required
          v-model="recipe.directions"
          name="directions"
          rows="7"
        />
      </div>
</div>

<div class="hstack gap-2">

            <div>
             <strong>Prep Time:</strong>
               <input type="text" class="form-control" id="prep_time" v-model=recipe.prep_time />
            </div>

              <div>
              <strong>  Cook Time: </strong>
              <input type="text" class="form-control" id="cook_time" v-model=recipe.cook_time />
              </div>

              <div>
              <strong>  Servings: </strong>
              <input type="text" class="form-control" id="servings" v-model=recipe.servings />
              </div>

              <div>
              <strong>  Servings Unit: </strong>
              <input type="text" class="form-control" id="serving_unit" v-model=recipe.serving_unit />
              </div>

        </div>

      <button @click="saveRecipe" class="btn btn-success">Save Recipe</button>
      
    </div>



    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newRecipe">Add</button>
    </div>
   
</template>

<script>
import RecipeDataService from "../services/RecipeDataService";

export default {
  name: "add-recipe",
  data() {
    return {
      recipe: {
        title: "",
        description: "",
        cuisine: "",
        course: "",
        tags: "",
        ingredients: "",
        directions: "",
        source: "",
        prep_time: "",
        cook_time: "",
        servings: "",
        serving_unit: "",
        ingredient_list: "",
  
      },
      selectedFiles: undefined,
        currentFile: undefined,
        progress: 0,
        message: "",
        fileInfos: [],
        submitted: false,
        published: false,
        url: null
    };
  },
  methods: {
    saveRecipe() {


      RecipeDataService.create(this.currentFile,this.recipe )
        .then(response => {
          this.recipe.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
        })
        .catch(e => {
          console.log(e);
        });
    },

    selectFile() {
      this.selectedFiles = this.$refs.file.files;
      this.currentFile = this.selectedFiles.item(0)
      this.url = URL.createObjectURL(this.currentFile);
        
    },

    
    newRecipe() {
      this.submitted = false;
      this.recipe = {};
    }
  }
};
</script>

<style>
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>