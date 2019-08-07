package com.kennen.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "client")
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class ClientEntity extends BaseEntity{

    private String grantTypes;

    private String clientId;

    private String clientSecret;

    private String scopes;

    private String resourceIds;

}
