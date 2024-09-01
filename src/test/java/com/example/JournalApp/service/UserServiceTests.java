package com.example.JournalApp.service;

import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Repositary.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @ValueSource(strings={
            "ram",
            "hanu",
            "silky"
    })
    public void testFindByUserName(String name){
        //assertEquals(4,2+2);
//        User user = userRepository.findByUserName("princy");
//        assertTrue(!user.getJournalEntries().isEmpty());
     //   assertNotNull( userRepository.findByUserName("princy"));

        assertNotNull(userRepository.findByUserName(name),"failed for "+name);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "3,4,7",
            "4,4,9"
    })
    public void testmy(int a,int b ,int expected){
        assertEquals(expected,a+b);
    }
}
