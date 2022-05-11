
import { reactive } from 'vue'

export const store = reactive({
  recipes: [],
  recipe: {
      author: "",
      cook_time: "",
      course: "",
      createDateTime: "",
      cuisine: "",
      description: "",
      directions: "",
      id: "",
      image_name: "",
      ingredients: [],
      prep_time: "",
      serving_unit: "",
      servings: "",
      source: "",
      tags: "",
      title: "",
      updateDateTime: "",
      userid: ""
  },
  currentFile: undefined,
  image_url: window.VUE_APP_IMAGE_SERVER_URL
})