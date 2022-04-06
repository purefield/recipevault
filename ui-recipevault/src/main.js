
import { createApp } from 'vue'
import App from './App.vue'
import RecipeEdit from './components/RecipeEdit.vue'



import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

import router from './router'



let app = createApp(App).use(router).mount('#app')
app.config.globalProperties.window = window

app.component('RecipeEdit', RecipeEdit)