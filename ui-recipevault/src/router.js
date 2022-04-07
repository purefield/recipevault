import { createWebHistory, createRouter } from "vue-router";

const routes =  [

  {
    path: "/recipe/:id",
    name: "recipe-details",
    component: () => import("./components/RecipeEdit")
  },
  {
    path: "/add",
    name: "add",
    component: () => import("./components/RecipeCreate")
  },
  {
    path: "/recipes",
    name: "recipes",
    component: () => import("./components/RecipesList")
  },
  {
    path: "/",
    name: "index",
    component: () => import("./components/RecipesList")
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;