package com.account.service;

import com.account.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {

    UserDto getLoggedInUser();
    String getLoggedUserCompany();
}
