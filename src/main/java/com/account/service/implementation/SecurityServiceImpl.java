package com.account.service.implementation;

import com.account.dto.UserDto;
import com.account.entity.User;
import com.account.entity.common.UserPrincipal;
import com.account.exception.AccountingException;
import com.account.repository.UserRepository;
import com.account.service.SecurityService;
import com.account.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {


    private final UserRepository userRepository;
    private final UserService userService;

    public SecurityServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AccountingException("User not found"));
        if (user == null) {
            throw new UsernameNotFoundException("This user does not exist");
        }
        return new UserPrincipal(user);
    }

    @Override
    public UserDto getLoggedInUser() {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(currentUsername);
    }

    @Override
    public String getLoggedUserCompany() {
        return this.getLoggedInUser().getCompany().getTitle();
    }

    @Override
    public boolean isCurrentUserRoot() {
        return getLoggedInUser().getRole().getDescription().equals("Root User");
    }


    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            return principal.getId();
        }

        return null; // Return null if the user ID is not available or not authenticated
    }


    @Override
    public Optional<Long> getCurrentAuditor() {
        Long currentUserId =this.getCurrentUserId();
        return Optional.ofNullable(currentUserId);
    }
}
