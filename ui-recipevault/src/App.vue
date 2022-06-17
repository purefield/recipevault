<template>
  <div id="app">
    
    <div class="container">
    
     <nav class="navbar navbar-light bg-light">

     

    <div class="d-flex justify-content-start">
       <a href="#" class="navbar-brand" @click="retrieveRecipes">Recipe Vault (Quarkified)</a>
    </div>

    <div class="d-flex justify-content-center">
      <form class="d-flex justify-content-center">
                 <input type="search" class="form-control" placeholder="Search by title" v-model="title"/>
                 
                 <button class="btn btn-outline-primary" type="button"
                   @click="searchTitle">
                     Search
                    </button>
                    <button class="btn btn-outline-primary" type="button"
                   @click="retrieveRecipes">
                     Clear
                    </button>
                    
              </form>
    </div>

    <div class="d-flex justify-content-end">
       <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createRecipe">New Recipe</button>
    </div>

    
    

      </nav>
  </div>


<!-- Modal -->
<div class="modal fade" id="createRecipe" tabindex="-1" aria-labelledby="createRecipeModal" aria-hidden="true">
  <RecipeCreate  @createRecipe="createRecipe"/>
  
</div>

    <div class="container mt-3">
      <router-view />
    </div>

    
</div>
</template>

<script>

import RecipeDataService from "./services/RecipeDataService";

import RecipeCreate from "./components/RecipeCreate.vue";




import { store } from './store.js';

export default {
  name: "app",
  modal: null,
  components: { RecipeCreate},
  emits: ['editMode'],

  data() {
    
    return {
      component: "",
      recipes: [],
      title: "",
      modal: null,
      store
    }
  },
  
   methods: {

searchTitle() {
      RecipeDataService.findByTitle(this.title)
        .then(response => {
          this.store.recipes = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },


retrieveRecipes() {
      // reset form
      this.title=""

      //get recipes
      RecipeDataService.getAll()
        .then(response => {
          this.store.recipes = response.data;
          //this.recipes = this.store.recipes
          console.log(response.data);
          this.$router.push("/");
        })
        .catch(e => {
          console.log(e);
        });
    },

  

  },
  

   }
</script>