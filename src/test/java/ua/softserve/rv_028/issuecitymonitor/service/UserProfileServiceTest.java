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
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileServiceTest {

    private User USER;
    private static final String USER_PASSWORD = "000";
    private static final String USER_NEW_PASSWORD = "newPassword";
    private static final String INCORRECT_USERNAME = "incorrectUsername";
    private static final String INCORRECT_PASSWORD = "incorrectPassword";
    private static final String newFirstName = "firstName";
    private static final String newLastName = "lastName";
    private static final String newPhone = "+10123456789";

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserDao dao;

    @Autowired
    private UserProfileService service;

    @Before
    public void setup(){
        USER = TestUtils.createUser(1);
        USER.setPassword(encoder.encode(USER.getPassword()));
        dao.save(USER);
    }

    @Test
    public void updatePassword() {
        service.updatePassword(USER.getUsername(), USER_PASSWORD, USER_NEW_PASSWORD);
        assertEquals(encoder.matches(USER_NEW_PASSWORD, dao.findUserByUsername(USER.getUsername()).getPassword()), true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updatePasswordFailIncorrectPass(){
        service.updatePassword(USER.getUsername(), INCORRECT_PASSWORD, USER_NEW_PASSWORD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void  updatePasswordFailEmptyPass(){
        service.updatePassword(USER.getUsername(), "", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void updatePasswordFailNullFields(){
        service.updatePassword(null, null, null);
    }

    @Test
    public void updateContactsInfo(){
        dao.save(USER);
        service.updateContactsInfo(USER.getUsername(), newFirstName, newLastName, newPhone);
        assertEquals(dao.findUserByUsername(USER.getUsername()).getFirstName(), newFirstName);
        assertEquals(dao.findUserByUsername(USER.getUsername()).getLastName(), newLastName);
        assertEquals(dao.findUserByUsername(USER.getUsername()).getPhone(), newPhone);
    }

    @Test
    public void updateContactsInfoOnlyFirstName(){
        dao.save(USER);
        service.updateContactsInfo(USER.getUsername(), newFirstName, null, null);
        assertEquals(dao.findUserByUsername(USER.getUsername()).getFirstName(), newFirstName);
        System.out.println(dao.findUserByUsername(USER.getUsername()));

    }

    @Test
    public void updateContactsInfoOnlyLastName(){
        dao.save(USER);
        service.updateContactsInfo(USER.getUsername(), null, newLastName, null);
        assertEquals(dao.findUserByUsername(USER.getUsername()).getLastName(), newLastName);
    }

    @Test
    public void updateContactsInfoOnlyPhone(){
        dao.save(USER);
        service.updateContactsInfo(USER.getUsername(), null, null, newPhone);
        assertEquals(dao.findUserByUsername(USER.getUsername()).getPhone(), newPhone);
    }

    @Test
    public void getUserInfo(){
        dao.save(USER);
        Map userInfo = service.getUserInfo(USER.getUsername());
        assertEquals(userInfo.get("email"), USER.getUsername());
        assertEquals(userInfo.get("firstName"), USER.getFirstName());
        assertEquals(userInfo.get("lastName"), USER.getLastName());
        assertEquals(userInfo.get("authorities"), USER.getAuthorities());
        assertEquals(userInfo.get("phone"), USER.getPhone());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserInfoFailEmptyUsername(){
        dao.save(USER);
        service.getUserInfo(INCORRECT_USERNAME);
    }

    @After
    public void cleanUp(){
        dao.deleteAll();
    }
}
