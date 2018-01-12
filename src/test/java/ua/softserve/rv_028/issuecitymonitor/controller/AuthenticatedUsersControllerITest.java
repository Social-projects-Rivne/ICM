package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticatedUsersControllerITest {

    private static User USER = TestUtils.createAdmin(0);
    private static final String USERNAME = "mock-test@mail.com";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String PHONE_NUMBER = "+10123456789";

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticatedUsersController controller;

    @Before
    public void setup(){
        USER.setUsername(USERNAME);
        User user = userDao.findUserByUsername(USERNAME);
        if (user == null)
            userDao.save(USER);
        else
            USER = user;
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void getUserName(){
        assertEquals(USERNAME, controller.getUserName());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void updateContactInfo(){
        userDao.save(USER);
        controller.updateContactInfo(FIRST_NAME, LAST_NAME, PHONE_NUMBER);

        User updatedUser = userDao.findUserByUsername(USER.getUsername());
        assertEquals(FIRST_NAME, updatedUser.getFirstName());
        assertEquals(LAST_NAME, updatedUser.getLastName());
        assertEquals(PHONE_NUMBER, updatedUser.getPhone());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void updateFirstName(){
        userDao.save(USER);
        controller.updateContactInfo(FIRST_NAME, null, null);

        User updatedUser = userDao.findUserByUsername(USER.getUsername());
        assertEquals(FIRST_NAME, updatedUser.getFirstName());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void updateLastName(){
        userDao.save(USER);
        controller.updateContactInfo(null, LAST_NAME, null);

        User updatedUser = userDao.findUserByUsername(USER.getUsername());
        assertEquals(LAST_NAME, updatedUser.getLastName());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void updatePhoneNumber(){
        userDao.save(USER);
        controller.updateContactInfo(null, null, PHONE_NUMBER);

        User updatedUser = userDao.findUserByUsername(USER.getUsername());
        assertEquals(PHONE_NUMBER, updatedUser.getPhone());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void updateContactsInfo(){
        controller.updateContactInfo(FIRST_NAME, LAST_NAME, PHONE_NUMBER);
        User user = userDao.findUserByUsername(USERNAME);
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(PHONE_NUMBER, user.getPhone());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void updateContactsInfoOnlyFirstName(){
        String newFN = "newFN";
        controller.updateContactInfo(newFN, null, null);
        assertEquals(newFN, userDao.findUserByUsername(USER.getUsername()).getFirstName());
    }


    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void updateContactsInfoOnlyPhone(){
        String newPhone = "+1987654321";
        controller.updateContactInfo(null, null, newPhone);
        assertEquals(newPhone, userDao.findUserByUsername(USER.getUsername()).getPhone());
    }


    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void getUserInfo(){
        Map userInfo = controller.getUserInfo();
        assertEquals(userInfo.get("email"), USER.getUsername());
        assertEquals(userInfo.get("firstName"), USER.getFirstName());
        assertEquals(userInfo.get("lastName"), USER.getLastName());
        assertEquals(userInfo.get("authorities"), USER.getAuthorities());
        assertEquals(userInfo.get("phone"), USER.getPhone());
    }
}
