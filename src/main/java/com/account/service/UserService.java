package com.account.service;

import com.account.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findByUsername(String currentUsername);

    List<UserDto> findAll();

    UserDto findById(Long id);

    void update(UserDto userdto);
}
