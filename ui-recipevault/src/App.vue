<template>
  <div id="app">
    <div class="container">
     <nav class="navbar navbar-expand-md navbar-dark bg-dark">
    
          <div class="container-fluid">
              <router-link to="/" class="navbar-brand">Recipe Vault (Quarkified)</router-link>
          
          <div class="collapse navbar-collapse">
        
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                  <router-link to="/add" class="nav-link">Add Recipe</router-link>
                </li>
              </ul>

              <form class="d-flex">
                 <input type="text" class="form-control" placeholder="Search by title" v-model="title"/>
                 <div class="input-group-append">
                 <button class="btn btn-outline-secondary" type="button"
                   @click="searchTitle">
                     Search
                    </button>
                    </div>
              </form>
          </div>
       </div>
      </nav>
  </div>

    <div class="container mt-3">
      <router-view />
    </div>

</div>
</template>

<script>

import RecipeDataService from "./services/RecipeDataService";

import { store } from './store.js';

export default {
  name: "app",

  data() {
    
    return {
      recipes: [],
      title: "",
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
    }
  }

   }
</script>