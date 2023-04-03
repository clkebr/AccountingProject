package com.account.entity;

import com.account.enums.ProductUnit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity{

    String name;
    int quantityInStock;
    int lowLimitAlert;

    @Enumerated(EnumType.STRING)
    ProductUnit productUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name ="category" )
    Category category;
}
