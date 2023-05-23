package com.account.service.implementation;

import com.account.dto.RoleDto;
import com.account.mapper.MapperUtil;
import com.account.repository.RoleRepository;
import com.account.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream()
                .map(entity->mapperUtil.convertToType(entity,new RoleDto()))
                .collect(Collectors.toList());
    }


}
