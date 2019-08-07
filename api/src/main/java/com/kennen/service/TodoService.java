package com.kennen.service;

import com.kennen.data.entity.TodoEntity;
import com.kennen.data.rqrs.TodoRequest;
import com.kennen.data.rqrs.TodoResponse;
import com.kennen.exception.KennenException;
import com.kennen.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService extends BaseService {

    private static final String PRIORITY_FILTER_ALL = "ALL";
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoCacheService todoCacheService;

    public List<TodoEntity> getList(String priority) {
        List<TodoEntity> list = getList();
        if (PRIORITY_FILTER_ALL.equals(priority))
            return list;

        List<TodoEntity> collect = list.stream()
                .filter(todo -> todo.getPriority().name().equals(priority))
                .collect(Collectors.toList());
        return collect;

    }

    private List<TodoEntity> getList(){
        List<TodoEntity> list = todoCacheService.getList(getUsername());
        return list;
    }

    /**
     * 등록 method
     * 최초 등록 시 prevId를 -1L로 등록한다.
     */
    public TodoEntity createTodo(TodoRequest todoRequest) {
        Long userId = getUserId();
        TodoEntity todoEntity = todoRequest.toCreate(userId);
        return add(todoEntity);
    }

    /**
     *  task 수정
     *  현재는 우선 순위만 변경 가능
     */
    public TodoResponse updateTodo(TodoRequest todoRequest) {
        Optional<TodoEntity> entity = todoRepository.findById(todoRequest.getId());
        return entity.map(todoEntity -> {
            todoEntity.setPriority(todoRequest.getPriority());
            todoRepository.save(todoEntity);
            return TodoResponse.fromEntity(todoEntity);
        }).orElseThrow(RuntimeException::new);
    }

    /**
     * task 삭제
     */
    public boolean deleteTodo(Integer removeIndex) {
        List<TodoEntity> list = getList();
        TodoEntity remove = remove(removeIndex, list);
        todoRepository.delete(remove);
        return true;
    }

    /**
     * 순서 변경 Method
     */
    public boolean moveTodo(int currentIndex, int targetIndex) {
        if (currentIndex == targetIndex)
            return true;

        List<TodoEntity> list = getList();

        //remove 메소드를 이용해서 기존 배열에서 제거 한 뒤, 추가 또는 삽입 할 때 사용한다.
        TodoEntity reMove = remove(currentIndex, list);
        if (targetIndex == list.size())
            add(reMove);                       // 이동하려는 위치가 배열의 마지막 일 경우 add
        else
            insert(targetIndex, reMove, list); // 이동하려는 위치가 배열의 중간 일 경우 insert
        return true;
    }

    /**
     * 삽입
     * <p>
     * 최초 A <- B <- C 의 상태에서 새로운 task 'D' 를 'B' 위치에 insert 할 경우
     * 1. D.prev = B.prev :: D.prev = 'A'
     * 2. B.prev = D.id :: B.prev = 'D'
     * 결과 : A <- D <- B <- C
     */
    private void insert(Integer insertIndex, TodoEntity insertTodo, List<TodoEntity> list) {
        //이동 할 index 에 위치한 task
        TodoEntity existEntity = list.get(insertIndex);

        //insert task 의 prev 값을 기존 task 의 prev 값으로 변경 뒤 저장
        insertTodo.setPrevId(existEntity.getPrevId());
        todoRepository.save(insertTodo);

        //기존 task 의 prev 값을 insert task 의 ID 값으로 변경 뒤 저장
        existEntity.setPrevId(insertTodo.getId());
        todoRepository.save(existEntity);

    }

    /**
     * 추가 Method
     * - task 생성 시 사용
     * - task 순서 변경 시 사용
     * :: 기존 task 가 존재하면 마지막 task 와 연결한다.
     */
    private TodoEntity add(TodoEntity todo) {
        List<TodoEntity> list = getList();
        if (!CollectionUtils.isEmpty(list)) {
            TodoEntity lastTask = CollectionUtils.lastElement(list);
            todo.setPrevId(lastTask.getId());
        }
        return todoRepository.save(todo);
    }

    /**
     * 삭제 Method
     * - task 삭제 시 사용
     * - task 순서 변경 시 사용
     */
    private TodoEntity remove(int removeIndex, List<TodoEntity> list) {
        TodoEntity removeTodo = list.get(removeIndex);
        list.stream()
                .filter(todo -> todo.getPrevId().equals(removeTodo.getId()))
                .findFirst()
                .ifPresent(todo -> {
                    /*
                     삭제 task 를 다른 task 가 참조하고 있을 경우
                     해당 task 의 prev 값을 삭제할 task 의 prev 값으로 변경한다.
                    */
                    todo.setPrevId(removeTodo.getPrevId());
                    todoRepository.save(todo);
                });

        return list.remove(removeIndex);
    }


}
