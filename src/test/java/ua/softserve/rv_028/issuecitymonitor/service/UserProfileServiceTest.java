package ua.softserve.rv_028.issuecitymonitor.service;

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
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileServiceTest {

    private static User USER;
    private static final String USER_PASSWORD = "000";
    private static final String USER_NEW_PASSWORD = "newPassword";
    private static final String INCORRECT_USERNAME = "incorrectUsername";
    private static final String INCORRECT_PASSWORD = "incorrectPassword";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE = "+10123456789";

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileService profile;

    @Autowired
    private RegistrationService registration;

    @Autowired
    private UserMapper mapper;

    @Before
    public void setup(){
        UserDto user = TestUtils.createUserDto(0);
        user.setEmail(UUID.randomUUID() + "@mail.com");
        USER = mapper.toEntity(registration.registrationUser(user));
    }

    @Test
    public void updatePassword() {
        profile.updatePassword(USER.getUsername(), USER_PASSWORD, USER_NEW_PASSWORD);
        assertEquals(encoder.matches(USER_NEW_PASSWORD, userDao.findUserByUsername(USER.getUsername()).getPassword()), true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updatePasswordFailIncorrectPass(){
        profile.updatePassword(USER.getUsername(), INCORRECT_PASSWORD, USER_NEW_PASSWORD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void  updatePasswordFailEmptyPass(){
        profile.updatePassword(USER.getUsername(), "", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void updatePasswordFailNullFields(){
        profile.updatePassword(null, null, null);
    }

    @Test
    public void updateContactsInfo(){
        profile.updateContactsInfo(USER.getUsername(), FIRST_NAME, LAST_NAME, PHONE);
        assertEquals(userDao.findUserByUsername(USER.getUsername()).getFirstName(), FIRST_NAME);
        assertEquals(userDao.findUserByUsername(USER.getUsername()).getLastName(), LAST_NAME);
        assertEquals(userDao.findUserByUsername(USER.getUsername()).getPhone(), PHONE);
    }

    @Test
    public void updateContactsInfoOnlyFirstName(){
        profile.updateContactsInfo(USER.getUsername(), FIRST_NAME, null, null);
        assertEquals(userDao.findUserByUsername(USER.getUsername()).getFirstName(), FIRST_NAME);
        System.out.println(userDao.findUserByUsername(USER.getUsername()));

    }

    @Test
    public void updateContactsInfoOnlyLastName(){
        profile.updateContactsInfo(USER.getUsername(), null, LAST_NAME, null);
        assertEquals(userDao.findUserByUsername(USER.getUsername()).getLastName(), LAST_NAME);
    }

    @Test
    public void updateContactsInfoOnlyPhone(){
        profile.updateContactsInfo(USER.getUsername(), null, null, PHONE);
        assertEquals(userDao.findUserByUsername(USER.getUsername()).getPhone(), PHONE);
    }

    @Test
    public void getUserInfo(){
        Map userInfo = profile.getUserInfo(USER.getUsername());
        assertEquals(userInfo.get("email"), USER.getUsername());
        assertEquals(userInfo.get("firstName"), USER.getFirstName());
        assertEquals(userInfo.get("lastName"), USER.getLastName());
        assertEquals(userInfo.get("authorities"), USER.getAuthorities());
        assertEquals(userInfo.get("phone"), USER.getPhone());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserInfoFailEmptyUsername(){
        profile.getUserInfo(INCORRECT_USERNAME);
    }
}
