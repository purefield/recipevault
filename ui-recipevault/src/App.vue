<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <router-link to="/" class="navbar-brand">Recipe Vault (Quarkified)</router-link>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link to="/add" class="nav-link">Add</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/recipes" class="nav-link">Recipes</router-link>
        </li>
        <!--<li class="nav-item">
        <div class="input-group mb-3" >
        <input type="text" class="form-control" placeholder="Search by title"
          v-model="title"/>
        <div class="input-group-append">
          <button class="btn btn-outline-secondary" type="button"
            @click="searchTitle"
          >
            Search
          </button>
        </div>
      </div>
      </li>
      -->
        
      </div>
    </nav>

    <div class="container mt-3">
      <router-view />
    </div>
  </div>
</template>

<script>





export default {
  name: "app",
  
};
</script>