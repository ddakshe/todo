package com.kennen.data.rqrs;

import com.kennen.data.entity.Priority;
import com.kennen.data.entity.TodoEntity;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TodoRequest implements Serializable {

    private static final long serialVersionUID = 5372870907356421969L;

    private Long id;
    private Long prevId;
    private Long userId;
    @NotBlank
    @Size(min = 1, max = 256)
    private String content;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    public TodoEntity toCreate(Long userId){
        return TodoEntity.builder()
                .content(this.content)
                .priority(Priority.NORMAL)
                .userId(userId)
                .prevId(-1L)
                .build();
    }
}
