<template>

  <div>

        <div class="input-group mb-3" >
        <input type="text" class="form-control" placeholder="Search by title"
          v-model="title"/>
        <div class="input-group-append">
          <button class="btn btn-outline-secondary" type="button"
            @click="searchTitle"
          >
            Search
          </button>
          <button class="btn btn-outline-secondary" type="button"
            @click="refreshList"
          >
            Clear
          </button>
        </div>
      </div>


<div class="card-group">


<div v-for="(recipe, index) in recipes" :key="index"  @click="setActiveRecipe(recipe, index)">
  <div class="col">
    <div class="card">
      <img v-bind:src="image_url + recipe.image_name" class="card-img-top" width="240" height="160"/> 
        <div class="card-body">
            <h5 class="card-title">{{recipe.title}}</h5> 
            
            
        </div>
    </div>
  </div>
</div>
</div>


</div>



<!-- Recipe Modal -->
<div class="modal fade" id="recipeModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">

    <RecipeView v-if="component === 'RecipeView'" v-bind=currentRecipe />
    <RecipeEdit v-if="component === 'RecipeEdit'" v-bind=currentRecipe />

        
      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      
      <div class="modal-footer">
       
       <button type="button" class="btn btn-primary" tag="button" @click="setEditRecipe()">Edit</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        
        
      </div>
    </div>
  </div>
    </div>
  </div>

  

</template>



<script>
import RecipeDataService from "../services/RecipeDataService";

import RecipeEdit from "./RecipeEdit.vue";
import RecipeView from "./RecipeView.vue";

import { Modal } from 'bootstrap';

export default {
  name: "recipe-list",
  modal: null,
  
  components: { RecipeEdit,RecipeView },
  data() {
    
    return {
      component: "RecipeView",
      recipes: [],
      currentRecipe: null,
      currentIndex: -1,
      title: "",
      image_url: ""
    };
  },
  methods: {
    retrieveRecipes() {
      RecipeDataService.getAll()
        .then(response => {
          this.recipes = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },

    refreshList() {
      this.retrieveRecipes();
      this.currentRecipe = null;
      this.currentIndex = -1;
    },

    setActiveRecipe(recipe, index) {
      this.currentRecipe = recipe;
      this.currentIndex = recipe ? index : -1;
      this.modal.show();
    },

    setEditRecipe() {
      console.log("setEditRecipe")
      this.component = "RecipeEdit"
      
    },

  
    removeAllRecipes() {
      RecipeDataService.deleteAll()
        .then(response => {
          console.log(response.data);
          this.refreshList();
        })
        .catch(e => {
          console.log(e);
        });
    },
    
    searchTitle() {
      RecipeDataService.findByTitle(this.title)
        .then(response => {
          this.recipes = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    }
  },
  mounted() {
    this.retrieveRecipes();
    this.image_url = window.VUE_APP_IMAGE_SERVER_URL
    this.modal = new Modal(document.getElementById('recipeModal'))
  }
};


</script>
