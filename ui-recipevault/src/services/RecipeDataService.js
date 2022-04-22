import http from "../http-common";

class RecipeDataService {


  getAll() {
    return http.get("/recipes");
  }

  get(id) {
    return http.get(`/recipes/${id}`);
  }

 /** Create Recipe  */
  create(file, recipe) {

    /** const recipe = JSON.stringify({
      title: data.title,
      description: data.description,
      ingredients: data.ingredients
    })*/

    recipe.ingredients = recipe.ingredient_list.split("\n") 

    const new_recipe = JSON.stringify(recipe)

    console.log("JSON recipe:" + new_recipe)

    let formData = new FormData();
  
  
      formData.append('filename',file.name);
      formData.append('mimetype',file.type)
      formData.append('file',file)
      formData.append('recipe',new_recipe)
  
      return http.post(`/recipes`, formData, {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      });
    }


  update(id, data) {

    const recipe = JSON.stringify({
      title: data.title,
      description: data.description
    })


  let formData = new FormData();

  //formData.append('filename',data.file.name);
  //formData.append('mimetype',data.file.type)
  //formData.append('file',data.file)
  formData.append('recipe',recipe)

    return http.put(`/recipes/${id}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    });
  }




  delete(id) {
    return http.delete(`/recipes/${id}`);
  }

  deleteAll() {
    return http.delete(`/recipes`);
  }

  findByTitle(title) {
    return http.get(`/recipes/title/${title}`);
  }
}

export default new RecipeDataService();