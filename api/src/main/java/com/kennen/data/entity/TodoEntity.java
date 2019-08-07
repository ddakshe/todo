package com.kennen.data.entity;

import com.kennen.data.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todo")
@ToString
@Getter
@Setter

public class TodoEntity extends BaseEntity {

    @Column(length = 256)
    private String content;

    private Long prevId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}


