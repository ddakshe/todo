<template>
  <div>
    <Menu v-on:setPriority="setPriority"/>
    <div id="todoContainer">
      <div id="todo" class="input-group mb-3 fixed-top">
        <input type="text" class="form-control" v-model="name" maxlength="256" v-on:keyup.enter="createTodo(name)"
               placeholder="이곳에 해야할 일을 작성해 주세요.(Enter 사용 추천)">
        <div class="input-group-append">
          <button class="btn btn-success" type="button" v-on:click="createTodo(name)">저장</button>
          <button class="btn btn-danger" type="button" v-on:click="removeInput">취소</button>
        </div>
      </div>
      <div class="container">
        <todo-list ref="todoList"/>
      </div>
    </div>
  </div>
</template>

<script>

  import axios from 'axios'
  import Menu from "../components/Menu"
  import TodoList from "../components/TodoList"

  export default {
    name: "Todo",
    components: {
      TodoList,
      Menu
    },
    methods: {
      createTodo(todo) {
        if (todo != null) {
          let data = JSON.stringify({
            content: todo
          })

          axios({
            method: 'post',
            url: 'http://localhost:8081/todo',
            data: data,
            headers: {
              'Content-Type': 'application/json'
            }
          }).then((response) => {
            if (response) {
              this.$refs.todoList.addTodo(response.data)
            }
            this.removeInput()
          })
        }
      },
      removeInput() {
        this.name = null;
      },
      setPriority(priority){
        this.$refs.todoList.setPriority(priority)
        // alert(priority)
      }
    },
    data() {
      return {
        name: null,
      }
    }
  }
</script>

<style scoped>
  #todo {
    margin-top: 56px;
  }

  #todoContainer {
    margin-top: 130px;
    alignment: center;
  }
</style>
