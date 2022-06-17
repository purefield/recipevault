<template>
  <div id="app">
    <div class="container">
     <nav class="navbar navbar-expand-md navbar-dark bg-dark">
    
          <div class="container-fluid">
              <a href="#" class="navbar-brand" @click="retrieveRecipes">Recipe Vault (Quarkified)</a>
          
          <div class="collapse navbar-collapse">
        
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createRecipe">New Recipe</button>
                 
                </li>
              </ul>

              <form class="d-flex">
                 <input type="text" class="form-control" placeholder="Search by title" v-model="title"/>
                 <div class="input-group-append">
                 <button class="btn btn-outline-secondary" type="button"
                   @click="searchTitle">
                     Search
                    </button>
                    <button class="btn btn-outline-secondary" type="button"
                   @click="retrieveRecipes">
                     Clear
                    </button>
                    </div>
              </form>
          </div>
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