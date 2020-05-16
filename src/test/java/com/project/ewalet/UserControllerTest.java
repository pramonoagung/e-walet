package com.project.ewalet;


import com.project.ewalet.controller.UserController;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    Mockito mockito;

    @InjectMocks
    UserMapper userMapper;

    User userExpected() {
        User user = new User();
        user.setId(1);
        user.setEmail("habibyafi45@gmail.com");
        user.setPassword("Password123!");
        user.setFirst_name("Habib");
        user.setLast_name("Yafi");
        user.setPhone_number("6281373951739");
        user.setStatus(1);
        return user;
    }

    @Test
    public void loginTest() {
        mockito.when(userMapper.findByPhoneNumber("6281373951739")).thenReturn(
                userExpected()
        );
        Assert.assertEquals(userExpected(), userMapper.findByPhoneNumber("6281373951739"));
    }
}
