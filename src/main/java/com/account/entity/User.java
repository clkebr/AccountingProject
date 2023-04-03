package com.account.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
@Table(name = "roles")
public class User extends BaseEntity{

    @Column(unique = true)
    String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    String firstname;
    String lastname;
    String phone;
    boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company;
}
