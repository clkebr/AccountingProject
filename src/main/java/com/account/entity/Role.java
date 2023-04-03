package com.account.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Data
@Table(name = "roles")
public class Role extends BaseEntity{
    String description;
}
