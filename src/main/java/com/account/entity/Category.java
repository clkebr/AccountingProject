package com.account.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity{

    String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "company_id")
    Company company;
}
