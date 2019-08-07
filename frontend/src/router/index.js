import Vue from 'vue'
import Router from 'vue-router'
import Todo from "../pages/Todo";
import HelloTodo from "../pages/HelloTodo";
import Login from "../pages/Login";
import {store} from "../store/store"

const VueCookie = require('vue-cookie')

Vue.use(VueCookie)
Vue.use(Router)

const requireAuth = () => (from, to, next) => {
  let authenticated = isAuthenticated();
  if (authenticated) return next()
  next('/login?returnPath=' + from.name)
}



export function isAuthenticated() {
  return store.getters.isAuthenticated;
}

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloTodo',
      component: HelloTodo
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/todo',
      name: 'Todo',
      component: Todo,
      beforeEnter: requireAuth()
    }
  ]
})
