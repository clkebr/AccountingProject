package com.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {


    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String confirmPassword;
    private String phone;
    private RoleDto role;
    private CompanyDto company;
    private Boolean isOnlyAdmin;
}
