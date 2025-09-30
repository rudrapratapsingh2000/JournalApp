package com.edigest.journalApp.service;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("dev")
public class UserDetailServiceImplTest {

    @Mock
//    @MockBean
    UserRepository userRepository;
    //    @Autowired
    @InjectMocks
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @BeforeEach
    @Disabled
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Disabled
    void loadUserByUsernameTest() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("001").password("abc001").roles(new ArrayList<>()).build());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("001");
        Assertions.assertNotNull(userDetails);
    }
}
