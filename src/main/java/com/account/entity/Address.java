package com.account.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "addresses")
public class Address extends BaseEntity{

    String addressLine1;
    String addressLine2;
    String city;
    String state;
    String country;
    String zipCode;
}
