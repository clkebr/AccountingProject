package com.account.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Setter
public class RoleDto {

    private Long id;
    private String description;
}
