package com.account.dto;

import com.account.entity.User;
import com.account.validation.Unique;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class UserDto {


    private Long id;

    @NotBlank
    @Size(max = 50, min = 2)
    private String firstname;

    @NotBlank
    @Size(max = 50, min = 2)
    private String lastname;

    @Unique(fieldName = "username",entityClass = User.class)
    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String password;


    @NotNull
    private String confirmPassword;

    @NotBlank
    @Pattern(regexp = "^\\+\\d{1,3} \\(\\d{3}\\) \\d{3}-\\d{4}$")
    private String phone;

    @NotNull
    private RoleDto role;

    @NotNull
    private CompanyDto company;

    private Boolean isOnlyAdmin;
}
