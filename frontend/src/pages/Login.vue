<template>
  <div>
    <Menu/>
    <div class="container">
      <h2>Todo Login Page</h2>
      <p>In this example, we use <code>.was-validated</code> to indicate what's missing before submitting the form:</p>
      <form @submit.prevent="onSubmit(username, password)" action="/action_page.php" class="was-validated">
        <div class="form-group">
          <label for="uname">Username:</label>
          <input type="text" class="form-control" id="uname" v-model="username" placeholder="Enter username"
                 name="username" required>
          <div class="valid-feedback">Valid.</div>
          <div class="invalid-feedback">Please fill out this field.</div>
        </div>
        <div class="form-group">
          <label for="pwd">Password:</label>
          <input type="password" class="form-control" id="pwd" v-model="password" placeholder="Enter password"
                 name="password" required>
          <div class="valid-feedback">Valid.</div>
          <div class="invalid-feedback">Please fill out this field.</div>
        </div>
        <div class="form-group form-check">
          <label class="form-check-label">
            <input class="form-check-input" type="checkbox" name="remember" required checked> I agree on blabla.
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Check this checkbox to continue.</div>
          </label>
        </div>
        <button type="submit" class="btn btn-primary">로그인</button>
      </form>
    </div>
  </div>
</template>

<script>

    import Menu from "../components/Menu"

    export default {
        name: "Login",
        components: {
            Menu
        },
        data() {
            return {
                username: 'kennen@google.com',
                password: 'password'
            }
        },
        methods: {
            onSubmit(username, password) {
                this.$store.dispatch('LOGIN', {username, password})
                    .then(() => this.redirect())
                    .catch(reason => alert(reason))
            },
            redirect() {
                let returnPath = this.$route.query.returnPath;
                // 리다이렉트 처리
                if (returnPath) {
                    this.$router.push(returnPath)
                } else {
                    this.$router.push("/")
                }
            }
        }
    }
</script>

<style scoped>
.container{
  margin-top: 130px;
}
</style>
