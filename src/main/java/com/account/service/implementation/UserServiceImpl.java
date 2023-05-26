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
                .filter(entity-> !entity.getIsDeleted())
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
    public void update(UserDto dto) {

        User entity = userRepository.findById(dto.getId()).get();
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(dto.getLastname());
        entity.setPhone(dto.getPhone());
        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setRole(mapperUtil.convertToType(dto.getRole(), new Role()));


        userRepository.save(entity);

    }

    @Override
    public void save(UserDto userDto) {
        User user = mapperUtil.convertToType(userDto, new User());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setEnabled(true);

        userRepository.save(user);

    }

    @Override
    public void deleteUserById(Long id) {
        User byId = userRepository.findById(id).get();
        byId.setIsDeleted(true);
        userRepository.save(byId);

    }


}
