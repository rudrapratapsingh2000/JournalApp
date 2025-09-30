package com.edigest.journalApp.service;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    @Disabled
    public void testAdd() {
        assertEquals(4, 2 + 2);
        User user = userRepository.findByUserName("001");
        assertEquals("001", user.getUserName());
    }

    @Test
    @Disabled
    public void findByUserName() {
        assertNotNull(userRepository.findByUserName("001"));
        User user = userRepository.findByUserName("001");
        assertTrue(!user.getJournalEntities().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "10,20,30",
            "3,3,6"
    })
    @Disabled
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

    @ParameterizedTest
    @CsvSource({
            "001",
            "002",
            "admin"
    })
    @Disabled
    public void testFindByUserName01(String name) {
        assertNotNull(userRepository.findByUserName(name), "Failed for " + name);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "001",
            "002",
            "admin"
    })
    @Disabled
    public void testFindByUserName02(String name) {
        assertNotNull(userRepository.findByUserName(name), "Failed for " + name);
    }

    @ParameterizedTest
    @Disabled
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testsaveNewEntry(User user) {
        assertTrue(userService.saveNewEntry(user));
    }
}
