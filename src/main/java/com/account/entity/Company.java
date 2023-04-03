package com.account.entity;

import com.account.enums.CompanyStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
@Table(name = "companies")
public class Company extends BaseEntity{
    @Column(unique = true)
    String title;
    String phone;
    String website;
    CompanyStatus companyStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="address_id")
    Address address;
}
