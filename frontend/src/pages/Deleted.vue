<template>
  <div>
    <Menu/>
    <div class="container">
      <h1>Deleted list</h1>
      <todo-list/>
    </div>
  </div>
</template>

<script>
  import Menu from '../components/Menu'
  import TodoList from '../components/TodoList'

  export default {
    name: "Deleted",
    components: {
      Menu, TodoList
    }
  }
</script>

<style scoped>

</style>
