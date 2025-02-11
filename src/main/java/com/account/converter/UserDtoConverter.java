package com.account.converter;

import com.account.dto.UserDto;
import com.account.service.UserService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserDtoConverter implements Converter<String, UserDto> {

	private final UserService userService;

	public UserDtoConverter(@Lazy UserService userService) {
		this.userService = userService;
	}

	//    @SneakyThrows
	@Override
	public UserDto convert(String id) {
		if (id == null || id.isBlank())
			return null;
		return userService.findById(Long.parseLong(id));
	}

}