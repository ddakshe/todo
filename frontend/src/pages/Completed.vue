<template>
  <div>
    <Menu/>
    <div class="container">
      <h1>Completed list</h1>
      <todo-list/>
    </div>
  </div>
</template>

<script>
  import Menu from '../components/Menu'
  import TodoList from '../components/TodoList'

  export default {
    name: "Completed",
    components: {
      Menu, TodoList
    }
  }
</script>

<style scoped>

</style>
