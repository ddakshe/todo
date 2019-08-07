package com.kennen.repository;

import com.kennen.data.entity.Priority;
import com.kennen.data.entity.TodoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends CrudRepository<TodoEntity, Long> {

    List<TodoEntity> findAllByUserId(Long id);

    @Modifying
    @Query("update TodoEntity set priority = :priority where id = :id")
    int updateTodo(@Param("id") long id, @Param("priority") Priority priority);
}
