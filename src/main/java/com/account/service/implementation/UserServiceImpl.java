package com.account.service.implementation;

import com.account.dto.UserDto;
import com.account.entity.Role;
import com.account.entity.User;
import com.account.mapper.MapperUtil;
import com.account.repository.UserRepository;
import com.account.service.SecurityService;
import com.account.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MapperUtil mapperUtil;

    private final SecurityService securityService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, @Lazy SecurityService securityService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto findByUsername(String username) {
        UserDto userDto = mapperUtil.convertToType(userRepository.findByUsername(username), new UserDto());

        if (isAdmin(userDto)) userDto.setIsOnlyAdmin(true);

        return userDto;

    }

    @Override
    public List<UserDto> findAll() {

        List<User> userList;

        if (securityService.isCurrentUserRoot()) {
            userList = userRepository.findAllByRoleDescriptionOrderByCompanyTitleAsc("Admin");
        } else {
            userList = userRepository.findAllByCompanyTitleOrderByRole(securityService.getLoggedUserCompany());
        }

        return userList.stream()
                .map(entity -> {
                    UserDto dto = mapperUtil.convertToType(entity, new UserDto());
                    dto.setIsOnlyAdmin(isAdmin(dto));
                    return dto;
                })
                .collect(Collectors.toList());

    }

    private Boolean isAdmin(UserDto userDto) {
        return userDto.getRole().getDescription().equals("Admin");
    }



    @Override
    public UserDto findById(Long id) {

        UserDto userDto = mapperUtil.convertToType(userRepository.findById(id), new UserDto());
        userDto.setIsOnlyAdmin(isAdmin(userDto));
        return userDto;
    }

    @Override
    public void update(UserDto object) {

        User entity = userRepository.findById(object.getId()).get();
        entity.setFirstname(object.getFirstname());
        entity.setLastname(object.getLastname());
        entity.setPhone(object.getPhone());
        entity.setUsername(object.getUsername());
        entity.setPassword(object.getPassword());
        entity.setRole(mapperUtil.convertToType(object.getRole(), new Role()));
        entity.setLastUpdateDateTime(LocalDateTime.now());

        userRepository.save(entity);

    }

    @Override
    public void save(UserDto userDto) {
        User user = mapperUtil.convertToType(userDto, new User());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        System.out.println("user.getLastUpdateUserId() = " + user.getLastUpdateUserId());
        System.out.println("user.lastUpdateDateTime = " + user.lastUpdateDateTime);

        userRepository.save(user);

    }


}
