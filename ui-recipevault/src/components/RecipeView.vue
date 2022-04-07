<template>

<div class="modal-dialog modal-dialog-scrollable">
    <div class="modal-content">

      <div class="modal-header">
            
            <img v-bind:src="store.image_url + store.recipe.image_name" class="card-img-top" /> 
         
        </div>

    <div class="modal-body">

    <h5 class="modal-title">{{store.recipe.title}}</h5>

         <div>
          {{store.recipe.description}}
        </div>

        <div>
          <label><strong>Cuisine:</strong>{{store.recipe.cuisine}}</label> 
        </div>

        <div>
          <label><strong>Ingredients</strong>{{store.recipe.ingredients}}</label> 
        </div>

        <div>
          <label><strong>Cook Time:</strong>{{store.recipe.cook_time}}</label> 
        </div>

        <div>
          <label><strong>Author:</strong>{{store.recipe.author}}</label> 
        </div>

        <div>
          <label><strong>Course:</strong>{{store.recipe.course}}</label> 
        </div>

        <div>
          <label><strong>Directions:</strong>{{store.recipe.directions}}</label> 
        </div>

        <div>
          <label><strong>Serving Units:</strong>{{store.recipe.prep_time}}</label> 
        </div>

        <div>
          <label><strong>Servings:</strong>{{store.recipe.course}}</label> 
        </div>

        <div>
          <label><strong>Source:</strong>{{store.recipe.source}}</label> 
        </div>


    </div>
      </div>
       </div>

</template>


<script>

import { store } from '../store.js';

export default {
  name: "recipe-view",
  data() {
    return {
      message: '',
      store
    };
  }
}

</script>