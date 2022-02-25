import axios from "axios";

export default axios.create({
  baseURL: process.env.VUE_APP_RECIPE_DATA_SERVICE
});