// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import {store} from './store/store'
import axios from 'axios'
import toast from "./services/toast";


axios.interceptors.response.use(
  function(response) {
    toast.success(response.config.method);
    return response;
    },
  function(error) {
    // handle error
    if (error.response) {
      toast.error(error.response.status);
    }
  });

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  store,
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})
