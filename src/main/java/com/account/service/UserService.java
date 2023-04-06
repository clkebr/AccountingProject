package com.account.service;

import com.account.dto.UserDto;

public interface UserService {
    UserDto findByUsername(String currentUsername);
}
