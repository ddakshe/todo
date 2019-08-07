package com.kennen.controller;

import com.kennen.data.rqrs.TodoRequest;
import com.kennen.data.rqrs.TodoResponse;
import com.kennen.exception.KennenException;
import com.kennen.service.TodoService;
import com.kennen.data.entity.TodoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;


    @GetMapping
    public List<TodoEntity> list(@RequestParam String priority) {
        List<TodoEntity> list = todoService.getList(priority);
        return list;
    }

    @PostMapping
    public TodoResponse create(@RequestBody @Valid TodoRequest todoRequest) {
        TodoEntity todoEntity = todoService.createTodo(todoRequest);
        return TodoResponse.fromEntity(todoEntity);
    }

    @PatchMapping
    public boolean move(@RequestParam Integer currentIndex, @RequestParam Integer targetIndex) {
        return todoService.moveTodo(currentIndex, targetIndex);
    }

    @DeleteMapping
    public boolean remove(@RequestParam Integer deleteIndex){
        return todoService.deleteTodo(deleteIndex);
    }

    @PutMapping
    public TodoResponse update(@RequestBody @Valid TodoRequest todoRequest){
        return todoService.updateTodo(todoRequest);
    }
}
