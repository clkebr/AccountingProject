package com.account.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data @NoArgsConstructor
@Table(name = "invoice_products")
public class InvoiceProduct extends BaseEntity{

    int quantity;
    BigDecimal price;
    int tax;
    BigDecimal profitLoss;
    int remainingQty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Product  product;
}
