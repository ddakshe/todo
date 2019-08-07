<template>
  <div>
    <Menu/>
    <div class="container">
      <h1>Welcome Todo</h1>
      <h2>Task 관리 프로그램 입니다.</h2>
      <h3>간편한 테스트를 위해 로그인 form 값은 채워져 있습니다.</h3>
      <h4>ID : kennen@google.com, PW : password</h4>
      <br><br>
      <h2>화면 설명</h2>
      <h4>색상별 긴급도</h4>
      <ul class="list-group list-group-horizontal">
        <li class="list-group-item list-group-item-danger">상</li>
        <li class="list-group-item list-group-item-warning">중</li>
        <li class="list-group-item list-group-item-info">하</li>
      </ul>
      <br>
      <h4>순서 이동</h4>
      <p>리스트를 드래그 하여 변경</p>
      <h4>삭제</h4>
      <button type="button" class="btn btn-danger" v-on:click="deleteTodo(index)">삭제
      </button>
      <br><br>
      <h4>긴급도 변경</h4>
      <button type="button" class="btn btn-primary" v-on:click="changePriority(index)">↕
      </button>

    </div>
  </div>
</template>

<script>
  import Menu from '../components/Menu'


  export default {
    name: "HelloTodo",
    components: {
      Menu
    },
    created() {

    }
  }
</script>

<style scoped>

</style>
