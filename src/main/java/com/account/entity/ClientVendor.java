package com.account.entity;

import com.account.enums.ClientVendorType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
@Table(name = "client_vendors")
public class ClientVendor extends BaseEntity{

    String clientVendorName;
    String phone;
    String website;

    @Enumerated(EnumType.STRING)
    ClientVendorType clientVendorType;
    @OneToOne
    @JoinColumn(name = "address_id")
    Address address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    Company company;
}
