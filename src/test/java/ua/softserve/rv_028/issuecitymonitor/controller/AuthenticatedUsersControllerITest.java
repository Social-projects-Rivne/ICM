package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.UserProfileService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticatedUsersControllerITest {

    private static final User USER = TestUtils.createAdmin(0);
    private static final String newFirstName = "newFirstName";
    private static final String newLastName = "newLastName";
    private static final String newPhoneNumber = "+88005553535";


    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileService service;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private AuthenticatedUsersController controller;

    @Before
    public void setup(){
        userDao.save(USER);
    }

    @Test
    @WithMockUser(username = "mail@mail.ua0")
    public void getUserName(){
        System.out.println(controller.getUserName());
    }

    @Test
    @WithMockUser(username = "mail@mail.ua0")
    public void updateContactInfo(){
        userDao.save(USER);
        controller.updateContactInfo(newFirstName, newLastName, newPhoneNumber);

        User updatedUser = userDao.findUserByUsername(USER.getUsername());
        assertEquals(newFirstName, updatedUser.getFirstName());
        assertEquals(newLastName, updatedUser.getLastName());
        assertEquals(newPhoneNumber, updatedUser.getPhone());
    }

    @Test
    @WithMockUser(username = "mail@mail.ua0")
    public void updateFirstName(){
        userDao.save(USER);
        controller.updateContactInfo(newFirstName, null, null);

        User updatedUser = userDao.findUserByUsername(USER.getUsername());
        assertEquals(newFirstName, updatedUser.getFirstName());
    }

    @Test
    @WithMockUser(username = "mail@mail.ua0")
    public void updateLastName(){
        userDao.save(USER);
        controller.updateContactInfo(null, newLastName, null);

        User updatedUser = userDao.findUserByUsername(USER.getUsername());
        assertEquals(newLastName, updatedUser.getLastName());
    }

    @Test
    @WithMockUser(username = "mail@mail.ua0")
    public void updatePhoneNumber(){
        userDao.save(USER);
        controller.updateContactInfo(null, null, newPhoneNumber);

        User updatedUser = userDao.findUserByUsername(USER.getUsername());
        assertEquals(newPhoneNumber, updatedUser.getPhone());
    }

    @After
    public void cleanup(){
        userDao.deleteAll();
    }
}
