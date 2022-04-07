
import { reactive } from 'vue'

export const store = reactive({
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
  image_url: window.VUE_APP_IMAGE_SERVER_URL
})