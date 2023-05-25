package com.account.service.implementation;

import com.account.dto.RoleDto;
import com.account.entity.Role;
import com.account.mapper.MapperUtil;
import com.account.repository.RoleRepository;
import com.account.service.RoleService;
import com.account.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream()
                .map(entity->mapperUtil.convertToType(entity,new RoleDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDto> findRoles() {
        List<Role> roleList;

        if(securityService.isCurrentUserRoot())
            roleList = Collections.singletonList(roleRepository.findByDescription("Admin"));
        else {
           roleList = roleRepository.findAll().stream()
                    .filter(role -> !role.getDescription().equals("Root User"))
                    .collect(Collectors.toList());

        }
        return roleList.stream()
                .map(role-> mapperUtil.convertToType(role,new RoleDto()))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto findById(Long id) {
        return mapperUtil.convertToType(roleRepository.findById(id), new RoleDto());
    }


}
