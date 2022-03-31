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


    <div v-for="recipe in recipes" :key="recipe._id">
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


     
 

      <ul class="list-group">
        <li class="list-group-item"
          :class="{ active: index == currentIndex }"
          v-for="(recipe, index) in recipes"
          :key="index"
          @click="setActiveRecipe(recipe, index)"
        >
        <img v-bind:src="image_url + recipe.image_name"/> 
        
          {{ recipe.title }}
        </li>
      </ul>



</div>

<div class="col-md-6">
      <div v-if="currentRecipe">
        <h4>Recipe</h4>
        <div>
          <label><strong>Title:</strong></label> {{ currentRecipe.title }}
        </div>
        <div>
          <label><strong>Description:</strong></label> {{ currentRecipe.description }}
        </div>
        <div>
          <label><strong>Status:</strong></label> {{ currentRecipe.published ? "Published" : "Pending" }}
        </div>

        <router-link :to="'/recipe/' + currentRecipe.id" class="badge badge-warning">Edit</router-link>
      </div>
      <div v-else>
        <br />
        <p>Please click on a Recipe...</p>
      </div>
    </div>
  

</template>



<script>
import RecipeDataService from "../services/RecipeDataService";

export default {
  name: "recipe-list",
  data() {
    return {
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
  }
};


</script>
