package com.kennen.service;

import com.kennen.data.entity.TodoEntity;
import com.kennen.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoCacheService extends BaseService {

    private static final long ROOT = -1L;
    @Autowired
    private TodoRepository todoRepository;

    @Cacheable(value = "todoList", key = "#username")
    public List<TodoEntity> getList(String username) {

        List<TodoEntity> allByUserId = todoRepository.findAllByUserId(getUserId());
        if (CollectionUtils.isEmpty(allByUserId))
            return new ArrayList<>();

        return sortedTodoList(allByUserId);
    }

    private List<TodoEntity> sortedTodoList(List<TodoEntity> allByUserId) {
        Predicate<TodoEntity> todoEntityPredicate = todo -> todo.getPrevId() == ROOT;
        Optional<TodoEntity> root = allByUserId.stream().filter(todoEntityPredicate).findFirst();
        Long search = root.orElseThrow(RuntimeException::new).getId();
        List<TodoEntity> sortedList = new ArrayList<>();
        sortedList.add(root.get());

        List<TodoEntity> childTodoList = allByUserId.stream().filter(todoEntityPredicate.negate()).collect(Collectors.toList());
        int sortedSize = 0;
        int todoListSize = childTodoList.size();
        while (sortedSize++ < todoListSize) {
            for (TodoEntity todo : childTodoList) {
                if (todo.getPrevId().equals(search)) {
                    sortedList.add(todo);
                    search = todo.getId();
                    childTodoList.remove(todo);
                    break;
                }
            }
        }
        return sortedList;
    }


    @CacheEvict(value = "todoList", key = "#username")
    public void refresh(String username){
        log.info("cache clear => " + username);
    }

}
