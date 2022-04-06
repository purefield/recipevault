<template>

<h5 class="modal-title">{{$attrs.title}}</h5>
    
      <div class="modal-body">
        <div>
         View Template
        </div>
        <div>
          <label><strong>Description:</strong>{{$attrs.cuisine}}</label> 
        </div>
        <div>
          <label><strong>Description:</strong>{{$attrs.id}}</label> 
        </div>
        
      </div>

</template>


<script>

export default {
  name: "recipe-view",
  props: ['recipe'],
  data() {
    return {
      message: '',
      currentRecipe : this.recipe
    };
  }
}

</script>