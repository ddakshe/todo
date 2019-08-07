<template>
  <div class="container">
    <div v-if="todoList == null || todoList.length == 0">
      할일이 없다구요????<br/>
      인사평가 기간이 얼마 남지 않았습니다.<br/>
    </div>
    <draggable class="list-group" tag="ul" v-model="todoList" v-bind="dragOptions" :move="onMove"
               @start="isDragging=true" @end="isDragging=false">
      <transition-group type="transition" :name="'flip-list'">
        <li class="list-group-item" :class="getPriorityClass(todo)" v-on:mouseup="todoClick()"
            v-for="(todo, index) in todoList" :key="todo.id">
          {{todo.content}}
          <span class="badge">{{todo.prevId}}</span>
          <button type="button" class="btn btn-danger float-right" v-on:click="deleteTodo(index)">삭제
          </button>
          <button type="button" class="btn float-right" :class="getButtonPriority(todo)" v-if="priority === 'ALL'"
                  v-on:click="changePriority(index)">
            {{priorities[todo.priority].explain}}
          </button>
        </li>
      </transition-group>
    </draggable>
  </div>
</template>

<script>
  import axios from 'axios'
  import draggable from 'vuedraggable'
  import toast from "../services/toast";

  export default {
    name: "TodoList",
    methods: {
      initTodoList() {
        axios({
          method: 'get',
          url: 'http://localhost:8081/todo',
          params: {priority: this.priority}
        }).then(response => {
          this.todoList = response.data
        }).catch((error) => console.log(error));
      },
      todoClick() {
        if (this.priority !== 'ALL')
          toast.alert('순서 변경이나 긴급도 수정은 전체 조회 상태에서만 가능합니다.')
      },
      deleteTodo(index) {
        axios({
          method: 'delete',
          url: 'http://localhost:8081/todo',
          params: {deleteIndex: index}
        }).then(response => {
          if (response.data) {
            this.todoList.splice(index, 1);
          }
        }).catch(reason => alert(reason.message))
      },
      getButtonPriority(todo){
        return this.priorities[todo.priority].buttonClass
      },
      getPriorityClass(todo) {
        return this.priorities[todo.priority].listClass
      },
      getNextpriority(todo){
        return this.priorities[todo.priority].next
      },
      changePriority(index) {
        let todo = this.todoList[index]
        todo.priority = this.getNextpriority(todo)
        axios({
          method: 'put',
          url: 'http://localhost:8081/todo',
          data: JSON.stringify(todo),
          headers: {
            'Content-Type': 'application/json'
          }
        })
      },
      onMove({relatedContext, draggedContext}) {
        this.fromIndex = draggedContext.index
        this.toIndex = draggedContext.futureIndex
      },
      addTodo(todo) {
        this.todoList.push(todo)
      },
      setPriority(priority) {
        this.priority = priority
        this.initTodoList()
        if (this.priority === 'ALL') {
          this.editable = true
        } else {
          this.editable = false
        }
      }
    },
    watch: {
      isDragging(newValue) {
        if (newValue) {
          this.delayedDragging = true;
          return;
        }
        this.$nextTick(() => {
          if (this.fromIndex == this.toIndex)
            return;

          axios({
            method: 'patch',
            url: 'http://localhost:8081/todo',
            params: {currentIndex: this.fromIndex, targetIndex: this.toIndex}
          }).then(value => console.log(value.data))
            .catch(reason => console.log(reason.message));

        });
      }
    },
    created() {
      this.initTodoList()
    },
    components: {
      draggable
    },
    computed: {
      dragOptions() {
        return {
          animation: 0,
          group: "description",
          disabled: !this.editable,
          ghostClass: "ghost"
        };
      },
    },
    data() {
      return {
        isDragging: false,
        editable: true,
        delayedDragging: false,
        todoList: null,
        fromIndex: null,
        toIndex: null,
        priority: 'ALL',
        priorities: {
          URGENCY: {
            name: 'URGENCY',
            explain: '상',
            next: 'NORMAL',
            listClass: 'list-group-item-danger',
            buttonClass: 'btn-danger'
          },
          NORMAL: {
            name: 'NORMAL',
            explain: '중',
            next: 'TRIVIAL',
            listClass: 'list-group-item-warning',
            buttonClass: 'btn-warning'
          },
          TRIVIAL: {
            name: 'TRIVIAL',
            explain: '하',
            next: 'URGENCY',
            listClass: 'list-group-item-info',
            buttonClass: 'btn-primary'
          }
        }
      }
    }
  }
</script>

<style scoped>

  .btn {
    padding: .175rem .55rem;
  }

  .list-group {
    min-height: 30px;
  }

  .list-group-item {
    min-height: 50px;
    cursor: move;
  }

  .list-group-item i {
    cursor: pointer;
  }

</style>
