package com.project.ewalet;


import com.project.ewalet.controller.UserController;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    Mockito mockito;

    @Mock
    UserMapper userMapper;

    User user = new User(1, "habibyafi45@gmail.com", "Password123!", "Habib", "Yafi", "6281373951739", 1, "timestamp");

    User userExpected() {
        User user = new User();
        user.setId(1);
        user.setEmail("habibyafi45@gmail.com");
        user.setPassword("Password123!");
        user.setFirst_name("Habib");
        user.setLast_name("Yafi");
        user.setPhone_number("6281373951739");
        user.setStatus(1);
        user.setCreated_at("timestamp");
        return user;
    }

    @Test
    public void loginVerifiedTest() {
        mockito.when(userMapper.findByPhoneNumber("6281373951739")).thenReturn(
                user
        );
        Assert.assertEquals(userExpected(), userMapper.findByPhoneNumber("6281373951739"));
    }
}
