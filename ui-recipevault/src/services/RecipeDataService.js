import http from "../http-common";

class RecipeDataService {


  getAll() {
    return http.get("/recipes");
  }

  get(id) {
    return http.get(`/recipes/${id}`);
  }

 
  create(data) {

    

    const recipe = JSON.stringify({
      title: data.title,
      description: data.description,
      ingredients: data.ingredients
    })

    console.log("JSON recipe:" + recipe)

    let formData = new FormData();
  
      //formData.append("title", data.title);
      //formData.append("description", data.description);
      formData.append('filename',data.file.name);
      formData.append('mimetype',data.file.type)
      formData.append('file',data.file)
      formData.append('recipe',recipe)
  
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