package com.account.entity;

import com.account.enums.ClientVendorType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
@Table(name = "clients_vendors")
public class ClientVendor extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;

    private String clientVendorName;
    private String phone;
    private String website;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
