package com.account.converter;

import com.account.service.RoleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import com.account.dto.RoleDto;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<String, RoleDto> {

	private final RoleService roleService;

	public RoleDtoConverter(@Lazy RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public RoleDto convert(String id) {
		return roleService.findById(Long.valueOf(id));
	}
}
