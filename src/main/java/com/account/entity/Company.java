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
    private String title;

    private String phone;

    private String website;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;


}
