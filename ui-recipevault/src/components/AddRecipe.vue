<template>



  <div class="submit-form">

<div>
<label class="btn btn-default">
  <input type="file" ref="file" @change="selectFile" />
    </label>
</div>

    <div id="preview">
    <img v-if="url" :src="url" />
  </div>


    <div v-if="!submitted">


    
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

      <div class="form-group">
        <label for="description">Description</label>
        <input
          class="form-control"
          id="info"
          required
          v-model="recipe.description"
          name="info"
        />
      </div>


      <div class="form-group">
        <label for="ingredients">Ingredients</label>
        <textarea
          class="form-control"
          id="info"
          required
          v-model="recipe.ingredient_list"
          name="ingredients"
        />
      </div>

      <button @click="saveRecipe" class="btn btn-success">Submit</button>
    </div>

    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newRecipe">Add</button>
    </div>
  </div>
</template>

<script>
import RecipeDataService from "../services/RecipeDataService";

export default {
  name: "add-recipe",
  data() {
    return {
      recipe: {
        id: null,
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
        ingredient_list: ""
        
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

    //ingredients[] =ingredient_list.split("\n")

      var data = {
        title: this.recipe.title,
        description: this.recipe.description,
        file: this.currentFile,
        ingredients : this.recipe.ingredient_list.split("\n") 
      };

      RecipeDataService.create(data)
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

    joinIngredients() {

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