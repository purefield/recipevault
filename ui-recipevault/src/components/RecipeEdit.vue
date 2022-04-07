<template>


  <div class="edit-form">
    <h4>{{store.recipe.title}}</h4>
    <form>
      <div class="form-group">
        <label for="title">Title</label>
        <input type="text" class="form-control" id="title"
          v-model=store.recipe.title
        />

        <label for="description">Title</label>
        <input type="text" class="form-control" id="description"
          v-model=store.recipe.description
        />
      </div>
      

     
    </form>

    <button class="badge badge-danger mr-2"
      @click="deleteRecipe"
    >
      Delete
    </button>

    <button type="submit" class="badge badge-success"
      @click="updateRecipe"
    >
      Update
    </button>
    <p>{{ message }}</p>
  </div>

  
</template>

<script>
import RecipeDataService from "../services/RecipeDataService";

import { store } from '../store.js';

export default {
  name: "recipe-edit",
  data() {
    return {
      message: '',
      store
    };
  },
  methods: {
    
    
   updateRecipe() {
      RecipeDataService.update(store.recipe.id, store.recipe)
        .then(response => {
          console.log(response.data);
          this.message = 'The recipe was updated successfully!';
        })
        .catch(e => {
          console.log(e);
        });
    },

    deleteRecipe() {
      RecipeDataService.delete(this.currentRecipe.id)
        .then(response => {
          console.log(response.data);
          this.$router.push({ name: "recipes" });
        })
        .catch(e => {
          console.log(e);
        });
    }
  },
  mounted() {
    this.message = '';
    
    
    
  }
};
</script>

<style>
.edit-form {
  max-width: 300px;
  margin: auto;
}
</style>