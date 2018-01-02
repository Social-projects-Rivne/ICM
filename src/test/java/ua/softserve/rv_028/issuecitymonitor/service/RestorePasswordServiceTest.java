package ua.softserve.rv_028.issuecitymonitor.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.dao.RestorePasswordDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.RestorePassword;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.exception.RestorePasswordException;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class RestorePasswordServiceTest {

    private User user;
    private User user2;
    private UserDto user2Dto;
    private static final String NO_TOKEN = "NO_TOKEN";
    private static final String TOKEN = "EXAMPLE_TOKEN";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestorePasswordDao restorePasswordDao;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RestorePasswordService restorePassword;

    @Before
    public void setup(){
        user = userDao.findAll().get(0);
        user2 = userDao.findAll().get(1);
        user2Dto = mapper.toDto(user2);
        restorePasswordDao.deleteByUser(user2);
        //restorePasswordDao.save(new RestorePassword(user2, TOKEN));
    }

    //TODO
    /*@Test
    public void createOrderRestorePasswordTest(){
        boolean response = restorePassword.createResetToken(user.getUsername());
        assertEquals(true, response);
    }*/

    @Test(expected = RestorePasswordException.class)
    public void createOrderRestorePasswordFailTest(){
        String NOT_EXIST_EMAIL = "no-email!";
        restorePassword.createResetToken(NOT_EXIST_EMAIL);
    }

    @Test(expected = RestorePasswordException.class)
    public void setNewPasswordTestEmptyUser(){
        UserDto emptyUser = new UserDto();
        restorePassword.setNewPasswordForUser(emptyUser.getEmail(), emptyUser.getPassword(), NO_TOKEN);
    }

    @Test(expected = RestorePasswordException.class)
    public void setNewPasswordTestEmptyPassword(){
        UserDto userEmptyPassword = mapper.toDto(user);
        userEmptyPassword.setPassword("");
        restorePassword.setNewPasswordForUser(userEmptyPassword.getEmail(), userEmptyPassword.getPassword(),
                NO_TOKEN);
    }

    @Test
    public void setNewPasswordTest(){
        String newPass = "new_pass";
        RestorePassword restorePassDto = restorePasswordDao.findByUser(user2);
        restorePassword.setNewPasswordForUser(user2Dto.getEmail(), newPass, restorePassDto.getToken());
    }
}
