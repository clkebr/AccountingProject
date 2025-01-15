package com.account.service;

import com.account.dto.UserDto;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService, AuditorAware<Long> {

	UserDto getLoggedInUser();

	String getLoggedUserCompany();

	boolean isCurrentUserRoot();
}
