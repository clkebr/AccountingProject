package com.account.service.implementation;

import com.account.dto.UserDto;
import com.account.mapper.MapperUtil;
import com.account.repository.UserRepository;
import com.account.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public UserDto findByUsername(String username) {
        return mapperUtil.convertToType(userRepository.findByUsername(username),new UserDto()) ;
    }
}
