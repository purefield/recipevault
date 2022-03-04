import axios from "axios";

export default axios.create({
  baseURL: window.VUE_APP_RECIPE_DATA_SERVICE
});