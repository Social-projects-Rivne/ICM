package ua.softserve.rv_028.issuecitymonitor.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.RestorePasswordDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.RestorePassword;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.exception.RestorePasswordException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestorePasswordServiceTest {

    private static User USER;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestorePasswordService service;

    @Autowired
    private RestorePasswordDao restorePassDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Before
    public void setup(){
        USER = userDao.save(TestUtils.createUser(0));
    }

    @Test
    public void setNewPasswordForUser(){
        restorePassDao.deleteByUser(USER);
        service.createResetToken(USER.getUsername());
        String token = restorePassDao.findByUser(USER).getToken();
        service.setNewPasswordForUser(USER.getUsername(), "newPassword", token);
        User updatedUser = userDao.findUserByUsername(USER.getUsername());
        assertEquals(true, encoder.matches("newPassword", updatedUser.getPassword()));
    }

    @Test(expected = RestorePasswordException.class)
    public void setNewPasswordForUserFail(){
        service.setNewPasswordForUser(null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void setNewPasswordForUserFailToken(){
        service.setNewPasswordForUser(USER.getUsername(), "newPassword", "incorrectPassword");
    }

    @Test
    public void createResetToken(){
        service.createResetToken(USER.getUsername());
        RestorePassword restorePassword = restorePassDao.findByUser(USER);
        assertEquals(false, restorePassword.getToken().isEmpty());
    }

    @After
    public void cleanup(){
        restorePassDao.deleteAll();
        userDao.deleteAll();
    }
}
