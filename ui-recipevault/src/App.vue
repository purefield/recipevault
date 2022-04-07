<template>
  <div id="app">
  <div class="container">
    <nav class="navbar navbar-expand-md navbar-dark bg-dark">
    
      <div class="container-fluid">
      <router-link to="/" class="navbar-brand">Recipe Vault (Quarkified)</router-link>
      
      
      <div class="collapse navbar-collapse">
      <ul class="navbar-nav">
        <li class="nav-item">
          <router-link to="/add" class="nav-link">Add Recipe</router-link>
        </li>
      </ul>
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





export default {
  name: "app",
  
};
</script>