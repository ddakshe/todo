import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import router from '../router'
import {isCookieEnabled, getCookie, setCookie, removeCookie} from 'tiny-cookie'

Vue.use(Vuex);

export const store = new Vuex.Store({
  state: {
    isAuthenticated: false,
    refresh_token: null,
    client_id: 'todo',
    client_secret: 'todo'
  },
  data (){
    return{
      client_id : 'todo',
      client_secret : 'todo'
    }
  },
  getters: {
    isAuthenticated: state => {
      return state.isAuthenticated
    }
  },
  actions: {
    LOGIN({commit}, {username, password}) {
      let form = new FormData()
      let clientId = this.state.client_id;
      let clientSecret = this.state.client_secret;
      form.append('grant_type', 'password')
      form.append('username', username)
      form.append('password', password)
      form.append('client_id', clientId)
      form.append('client_secret', clientSecret)

      let basicAuth = btoa(clientId + ':' + clientSecret);

      return axios({
        method: 'post', //you can set what request you want to be
        url: 'http://localhost:8082/oauth/token',
        data: form,
        headers: {
          Authorization: 'Basic ' + basicAuth
        }
      }).then(res => commit('LOGIN', res.data))
        .catch(reason => alert(reason.message));
    },
    REFRESH_LOGIN({commit}) {
      let refreshToken = this.state.refresh_token;
      if (refreshToken == null){
        router.push({name: 'Login'})
        return
      }
      let form = new FormData()
      form.append('grant_type', 'refresh_token')
      form.append('refresh_token', refreshToken)
      let basicAuth = btoa(this.state.client_id + ':' + this.state.client_secret);
      return axios({
        method: 'post',
        url: 'http://localhost:8082/oauth/token',
        data: form,
        headers: {
          Authorization: 'Basic ' + basicAuth
        }
      }).then(res => commit('LOGIN', res.data))
        .catch(reason => alert(reason.message))

    },
    LOGOUT({commit}) {
      commit('LOGOUT');
    }
  },
  mutations: {
    LOGIN(state, data) {
      let accessToken = data.access_token;
      let refreshToken = data.refresh_token;
      state.refresh_token = refreshToken
      state.isAuthenticated = true
      state.accessToken = accessToken
      axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
      setCookie('refreshToken', refreshToken, {expires: '120d'})
      setCookie('accessToken', accessToken, {expires: '20d'})
    },
    LOGOUT(state) {
      state.isAuthenticated = false
      axios.defaults.headers.common['Authorization'] = null;
      removeCookie('accessToken')
      router.push({name: 'HelloTodo'})
    },
    authenticate(state, accessToken) {
      state.isAuthenticated = true
      axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
    },
  }
});

const enhanceAccessToken = () => {
  let accessToken = getCookie("accessToken");
  let refreshToken = getCookie("refreshToken");

  if (accessToken != null){
    store.commit("authenticate", accessToken)
  }

  // if (refreshToken != null){
  //   store.state.refresh_token = refreshToken;
  //   store.dispatch("REFRESH_LOGIN")
  // }

  return;
}
enhanceAccessToken()
