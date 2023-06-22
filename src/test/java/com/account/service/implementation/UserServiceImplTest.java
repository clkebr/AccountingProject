package com.account.service.implementation;

import com.account.dto.CompanyDto;
import com.account.dto.RoleDto;
import com.account.dto.UserDto;
import com.account.entity.User;
import com.account.enums.CompanyStatus;
import com.account.exception.AccountingException;
import com.account.mapper.MapperUtil;
import com.account.repository.UserRepository;
import com.account.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

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

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));

        //when
        assert user != null;
        UserDto actualUser = userServiceImp.findByUsername(user.getUsername());

        // then
        assertThat(actualUser).usingRecursiveComparison()
                .ignoringFields("password", "confirmPassword")
                .isEqualTo(userDto);

    }
    @Test
    void should_throw_AccountingException_when_username_is_null() {

        // when
        // it throws exception since no mock of userRepository and userRepository.findByUsername("") returns null
        Throwable throwable = catchThrowable(() -> userServiceImp.findByUsername(""));
        // assertThrows also works, but we can also assert message with catchThrowable
        // assertThrows(AccountingException.class, ()-> userService.findUserById(1L));

        // then
        //assert throes exception
        assertNotNull(throwable);
        //assert throes exception type
        assertInstanceOf(AccountingException.class, throwable);
        //assert throes exception message
        assertEquals("User not found", throwable.getMessage());

    }
    @Test
    void should_findAll_Filtered_Users_When_user_is_root_happyPath() {
        //given
        List<UserDto> userDtos = Arrays.asList(
                TestDocumentInitializer.getUser("Admin",true),
                TestDocumentInitializer.getUser("Admin",true),
                TestDocumentInitializer.getUser("Admin",true),
                TestDocumentInitializer.getUser("Employee",false));
        userDtos.get(0).getCompany().setTitle("Zet");
        userDtos.get(1).getCompany().setTitle("Abc");
        userDtos.get(2).getCompany().setTitle("Ower");
        userDtos.get(3).getCompany().setTitle("Ower");

        List<User> userList = userDtos.stream()
                .filter(dto->dto.getRole().getDescription().equals("Admin"))
                .sorted(Comparator.comparing((UserDto u) -> u.getCompany().getTitle())
                        .thenComparing(u -> u.getRole().getDescription()))
                .map(userDto -> mapperUtil.convertToType(userDto, new User()))
                .collect(Collectors.toList());

        List<UserDto> expectedList = userDtos.stream()
                .filter(dto->dto.getRole().getDescription().equals("Admin"))
                .sorted(Comparator.comparing((UserDto u) -> u.getCompany().getTitle())
                        .thenComparing(u -> u.getRole().getDescription()))
                .collect(Collectors.toList());


        //when
        when(securityService.isCurrentUserRoot()).thenReturn(true);
        when(userRepository.findAllByRoleDescriptionOrderByCompanyTitleAsc("Admin")).thenReturn(userList);

        List<UserDto> actualList = userServiceImp.findAllFilteredUsers();

        //then
        assertThat(actualList).usingRecursiveComparison()
                .ignoringFields("password", "confirmPassword", "isOnlyAdmin")
                .isEqualTo(expectedList);
        actualList.forEach(
                userDto -> assertTrue(userDto.getIsOnlyAdmin())
        );


    }
    @Test
    void should_findAll_Filtered_Users_When_user_is_Admin_happyPath() {
        //given
        List<UserDto> userDtos = Arrays.asList(
                TestDocumentInitializer.getUser("Admin",true),
                TestDocumentInitializer.getUser("Manager",false),
                TestDocumentInitializer.getUser("Employee",false),
                TestDocumentInitializer.getUser("Employee",false),
                TestDocumentInitializer.getUser("Admin",true));
        userDtos.get(0).getCompany().setTitle("Zet");
        userDtos.get(1).getCompany().setTitle("Zet");
        userDtos.get(2).getCompany().setTitle("Zet");
        userDtos.get(3).getCompany().setTitle("Ower");
        userDtos.get(4).getCompany().setTitle("Ower");

        List<User> userList = userDtos.stream()
                .filter(userDto -> userDto.getCompany().getTitle().equals("Zet"))
                .sorted(Comparator.comparing((UserDto u) -> u.getCompany().getTitle())
                        .thenComparing(u -> u.getRole().getDescription()))
                .map(userDto -> mapperUtil.convertToType(userDto, new User()))
                .collect(Collectors.toList());

        List<UserDto> expectedList = userDtos.stream()
                .filter(userDto -> userDto.getCompany().getTitle().equals("Zet"))
                .sorted(Comparator.comparing((UserDto u) -> u.getCompany().getTitle())
                        .thenComparing(u -> u.getRole().getDescription()))
                .collect(Collectors.toList());


        //when
        when(securityService.isCurrentUserRoot()).thenReturn(false);
        when(securityService.getLoggedUserCompany()).thenReturn("Zet");
        when(userRepository.findAllByCompanyTitleOrderByRole("Zet")).thenReturn(userList);

        List<UserDto> actualList = userServiceImp.findAllFilteredUsers();

        //then
        assertThat(actualList).usingRecursiveComparison()
                .ignoringFields("password", "confirmPassword", "isOnlyAdmin")
                .isEqualTo(expectedList);
    }
    @Test
    void save_null_dto_throws_exception() {
        // option 1:
        // only checks exception which comes from mapperUtil
        assertThrows(IllegalArgumentException.class, () -> userServiceImp.save(null));

        // option 2 : better - check exception and message
        Throwable throwable = catchThrowable(() -> userServiceImp.save(null));
        assertInstanceOf(IllegalArgumentException.class, throwable);
        assertEquals("source cannot be null", throwable.getMessage());

    }
    @Test
    void should_delete_User_ById_() {
        //given
        UserDto userDto = TestDocumentInitializer.getUser("Employee", false);
        User user = mapperUtil.convertToType(userDto, new User());

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // then
        Throwable throwable = catchThrowable(() -> {
            assert user != null;
            userServiceImp.deleteUserById(user.getId());
        });
        assert user != null;
        assertTrue(user.getIsDeleted());
        assertNull(throwable);
    }

    @ParameterizedTest
    @MethodSource(value = "updateUser")
    void should_update_user_with_firstname_lastname_phone_username_password_role(UserDto userDto, User user) {

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        userServiceImp.update(userDto);

        //then
        assertThat(userDto).usingRecursiveComparison()
                .ignoringFields("password", "confirmPassword", "isOnlyAdmin","company")
                .isEqualTo(user);
    }

    @ParameterizedTest
    @MethodSource(value = "updateUser")
    void should_NOT_update_user_company(UserDto expectedUserDto, User actualUser) {

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(actualUser));
        userServiceImp.update(expectedUserDto);

        //then
        assert actualUser != null;
        assertEquals(expectedUserDto.getId(), actualUser.getId());
        assertNotEquals(expectedUserDto.getCompany().getTitle(), actualUser.getCompany().getTitle());


    }


    @ParameterizedTest
    @MethodSource(value = "email")
    void is_Email_Exist(UserDto userDto, User user, boolean expected) {

        // when
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        // then
        assertEquals(expected, userServiceImp.emailExist(userDto));

    }

    static Stream<Arguments> email(){
        // given
        UserDto userDto = TestDocumentInitializer.getUser("Admin",true);
        User user = new MapperUtil(new ModelMapper())
                .convertToType(userDto, new User());
        user.setId(2L);
        return Stream.of(
                arguments(userDto, user, true),
                arguments(userDto, null, false)
        );
    }
    static Stream<Arguments> updateUser(){
        // given
        UserDto expectedUserDto = TestDocumentInitializer.getUser("Admin",true);
        CompanyDto companyDto= TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
        companyDto.setTitle("ABC");
        User user = new MapperUtil(new ModelMapper())
                .convertToType(expectedUserDto, new User());
        expectedUserDto.setFirstname("adam");
        expectedUserDto.setLastname("Smith");
        expectedUserDto.setPhone("1234567890");
        expectedUserDto.setUsername("adam@test.com");
        expectedUserDto.setPassword("123rtz");
        expectedUserDto.setRole(new RoleDto(4L,"Employee"));
        expectedUserDto.setCompany(new CompanyDto());

        return Stream.of(arguments(expectedUserDto, user));
    }
}