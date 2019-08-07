package com.kennen.data;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Table(name = "user")
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class UserEntity extends BaseEntity{

    @Column(unique = true, nullable = false, length = 128)
    @Email
    private String email;

    @Column(length = 64)
    private String name;

    @Column(length = 128)
    private String password;
}
