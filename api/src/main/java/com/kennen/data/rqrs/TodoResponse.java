package com.kennen.data.rqrs;


import com.kennen.data.entity.TodoEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TodoResponse {

    private Long id;
    private String content;
    private Long prevId;
    private String priority;


    public static TodoResponse fromEntity(TodoEntity todoEntity){
        return builder()
                .content(todoEntity.getContent())
                .id(todoEntity.getId())
                .prevId(todoEntity.getPrevId())
                .priority(todoEntity.getPriority().name())
                .build();
    }
}
