package com.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, updatable = false)
    LocalDateTime insertDateTime;

    @Column(nullable = false, updatable = false)
    Long insertUserId;

    @Column(nullable = false)
    LocalDateTime lastUpdateDateTime;

    @Column(nullable = false)
    Long lastUpdateUserId;


    boolean isDeleted;
}
