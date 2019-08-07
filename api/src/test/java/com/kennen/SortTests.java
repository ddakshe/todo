package com.kennen;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SortTests {
    List<Todo> todoList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        todoList.add(new Todo(1, "1번째", null));
        todoList.add(new Todo(2, "2번째", 1));
        todoList.add(new Todo(3, "3번째", 2));
        todoList.add(new Todo(4, "4번째", 3));
        todoList.add(new Todo(5, "5번째", 4));
        todoList.add(new Todo(6, "6번째", 5));
    }

    private List<Todo> sortedList(List<Todo> todoList) {
        List<Todo> list = new ArrayList<>();
        list.addAll(todoList);
        int size = list.size();
        int count = 0;
        List<Todo> sortedList = new ArrayList<>();
        Integer search = null;
        while (sortedList.size() < size) {
            for (Todo todo : list) {
                count++;
                if (todo.parent == search) {
                    sortedList.add(todo);
                    search = todo.sn;
                    list.remove(todo);
                    break;
                }
            }
        }
        System.out.println("반복횟수 :" + count);
        System.out.println(sortedList);
        return sortedList;
    }

    @Test
    public void sortByChild() {
        Assert.assertEquals(sortedList(todoList).size(), todoList.size());
    }

    @Test
    public void addTodo() {
        List<Todo> list = sortedList(todoList);

        Todo lastDodo = list.get(list.size() - 1);
        Todo newTodo = new Todo(7, "7번째", lastDodo.getSn());
        list.add(newTodo);
        List<Todo> addedList = sortedList(list);
        System.out.println(addedList);
    }

    @Test
    public void insertTodo() {
        List<Todo> list = sortedList(todoList);
        int addIndex = 1;
        Todo newTodo = new Todo();
        newTodo.setSn(7);
        newTodo.setText("7");

        List<Todo> todoList = insert(addIndex, newTodo, list);
        System.out.println(todoList);

    }

    private List<Todo> insert(int targetIndex, Todo todoEntity, List<Todo> list){
        Todo todo = list.get(targetIndex);
        todoEntity.setParent(todo.getParent());
        todo.setParent(todoEntity.getSn());
        list.add(todoEntity);
        return sortedList(list);
    }

    @Test
    public void deleteTodo() {
        List<Todo> list = sortedList(todoList);
        int deleteIndex = 3;

        if (deleteIndex == list.size() - 1) {
            Todo todo = list.get(deleteIndex);
            list.remove(todo);
        }else{
            Todo currentTodo = list.get(deleteIndex - 1);
            Todo childTodo = list.get(deleteIndex);
            childTodo.setParent(currentTodo.getParent());
            list.remove(currentTodo);
        }

        List<Todo> todos = sortedList(list);
        System.out.println(todos);
    }

    @Test
    public void movoTodo() {
        List<Todo> list = sortedList(todoList);

        int fromIndex = 3;
        int toIndex = 1;

        Todo fromTodo = list.get(fromIndex);
        Todo toTodo = list.get(toIndex);

        System.out.println(list);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class Todo {
        private Integer sn;
        private String text;
        private Integer parent;


    }
}
