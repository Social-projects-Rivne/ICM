package ua.softserve.rv_028.issuecitymonitor.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.IssueCityMonitorApplication;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IssueCityMonitorApplication.class)
public class RestorePasswordServiceTest {

    private User user;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MapperService mapper;

    @Autowired
    private RestorePasswordService restorePassword;

    @Before
    public void setup(){
        user = userDao.findAll().get(0);
    }

    @Test
    public void createOrderRestorePasswordTest(){
        boolean response = restorePassword.createOrderRestorePassword(user.getUsername());
        assertEquals(true, response);
    }

    @Test
    public void createOrderRestorePasswordFailTest(){
        String NOT_EXIST_EMAIL = "no-email!";
        boolean response = restorePassword.createOrderRestorePassword(NOT_EXIST_EMAIL);
        assertEquals(false, response);
    }

    @Test(expected = IllegalStateException.class)
    public void setNewPasswordTestEmptyUser(){
        UserDto emptyUser = new UserDto();
        restorePassword.setNewPasswordForUser(emptyUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNewPasswordTestEmptyPassword(){
        UserDto userEmptyPassword = mapper.fromEntityToDto(user);
        userEmptyPassword.setPassword("");
        restorePassword.setNewPasswordForUser(userEmptyPassword);
    }
}
