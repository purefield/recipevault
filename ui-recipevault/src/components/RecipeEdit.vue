<template>


  <div class="edit-form">
    <h4>{{$attrs.title}}</h4>
    <form>
      <div class="form-group">
        <label for="title">Title</label>
        <input type="text" class="form-control" id="title"
          v-model=this.updatedRecipe.title
        />

        <label for="description">Title</label>
        <input type="text" class="form-control" id="description"
          v-model=this.updatedRecipe.description
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

export default {
  name: "recipe-edit",
  data() {
    return {
      message: '',
      updatedRecipe: 'null',
      title: "",
      id:""

      

    };
  },
  methods: {
    
    
   updateRecipe() {
      RecipeDataService.update(this.updatedRecipe.id, this.updatedRecipe)
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
    this.updatedRecipe = {
      title :this.$attrs.title,
      description :this.$attrs.description,
      id :this.$attrs.id,

    }
    
    
  }
};
</script>

<style>
.edit-form {
  max-width: 300px;
  margin: auto;
}
</style>