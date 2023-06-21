package com.account.service.implementation;

import com.account.dto.UserDto;
import com.account.entity.User;
import com.account.mapper.MapperUtil;
import com.account.repository.UserRepository;
import com.account.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());
    @Mock
    private SecurityService securityService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImp;




    @Test
    void should_find_by_username_happyPath() {
        //given
        UserDto userDto = TestDocumentInitializer.getUser("Admin", true);
        User user = mapperUtil.convertToType(userDto, new User());

        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.ofNullable(user));

        //when
        assert user != null;
        UserDto actualUser = userServiceImp.findByUsername(user.getUsername());

        // then
        assertThat(actualUser).usingRecursiveComparison()
                .ignoringFields("password", "confirmPassword")
                .isEqualTo(userDto);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void emailExist() {
    }
}