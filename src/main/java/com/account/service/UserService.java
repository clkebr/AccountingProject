package com.account.service;

import com.account.dto.UserDto;

import java.util.List;

public interface UserService {
	UserDto findByUsername(String currentUsername);

	List<UserDto> findAllFilteredUsers();

	UserDto findById(Long id);

	void update(UserDto userdto);


	void save(UserDto userDto);


	void deleteUserById(Long id);

	boolean emailExist(UserDto userDto);
}
