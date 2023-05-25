package com.account.service;

import com.account.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> findAll();


    List<RoleDto> findRoles();

    RoleDto findById(Long id);
}
