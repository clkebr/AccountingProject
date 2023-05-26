package com.account.service.implementation;

import com.account.repository.UserRepository;
import com.account.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ContextConfiguration(classes = {SecurityServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SecurityServiceImplTest {
    @Autowired
    private SecurityServiceImpl securityServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link SecurityServiceImpl#getCurrentAuditor()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCurrentAuditor() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.account.service.implementation.SecurityServiceImpl.getLoggedInUser(SecurityServiceImpl.java:39)
        //       at com.account.service.implementation.SecurityServiceImpl.getCurrentAuditor(SecurityServiceImpl.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        securityServiceImpl.getCurrentAuditor();
    }

    /**
     * Method under test: {@link SecurityServiceImpl#getCurrentAuditor()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCurrentAuditor2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.account.service.implementation.SecurityServiceImpl.getLoggedInUser(SecurityServiceImpl.java:39)
        //       at com.account.service.implementation.SecurityServiceImpl.getCurrentAuditor(SecurityServiceImpl.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        // TODO: Populate arranged inputs
        Optional<Long> actualCurrentAuditor = this.securityServiceImpl.getCurrentAuditor();

        // Assert
        // TODO: Add assertions on result
    }
}

