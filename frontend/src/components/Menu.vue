<template>
  <div>
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
      <a class="navbar-brand nav-link" href="#" v-if="isAuthenticated()" v-on:click="logout">{{loginText()}}</a>
      <router-link class="navbar-brand nav-link" to="/login" v-else>{{loginText()}}</router-link>
      <!-- Links -->
      <ul class="navbar-nav">
        <li class="nav-item">
          <router-link class="nav-link" to="/">Home</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/todo">todo</router-link>
        </li>
      </ul>
      <select class="selectpicker" @change="setPriority($event)">
        <option value="ALL">전체 조회</option>
        <option value="URGENCY">긴급</option>
        <option value="NORMAL">보통</option>
        <option value="TRIVIAL">하찮</option>
      </select>
    </nav>
  </div>
</template>

<script>
    export default {
        name: "Menu",
        data() {
            return {
                msg: "Basic panel example",
            }
        },
        methods: {
            isAuthenticated(){
                return this.$store.getters.isAuthenticated
            },
            loginText() {
                return this.isAuthenticated() ? "Logout" : "Login"
            },
            logout() {
                this.$store.dispatch("LOGOUT");
            },
            setPriority(value){
              this.$emit('setPriority', value.target.value)
              console.log(value.target.value)
            }
        }
    }
</script>

<style scoped>

</style>
