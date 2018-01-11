package ua.softserve.rv_028.issuecitymonitor.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.exception.RegistrationException;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationServiceTest {

    private User REGISTERED_USER = TestUtils.createUser(0);
    private UserDto NEW_USER = TestUtils.createUserDto(1);
    private static final String USERNAME = "mock-test@mail.com";
    private static final String GENERATED_USERNAME = UUID.randomUUID() + "@mail.com";

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RegistrationService service;

    @Before
    public void setup(){
        NEW_USER.setEmail(GENERATED_USERNAME);
        REGISTERED_USER.setUsername(USERNAME);
        User user = userDao.findUserByUsername(REGISTERED_USER.getUsername());
        if (user == null)
            userDao.save(REGISTERED_USER);
        else
            REGISTERED_USER = user;
    }

    @Test
    public void userExist(){
        boolean userExist = service.isPossibleRegistration(REGISTERED_USER.getUsername());
        assertEquals(true, userExist);
    }

    @Test
    public void userExistFalse(){
        boolean userExist = service.isPossibleRegistration("no-email");
        assertEquals(false, userExist);
    }

    @Test(expected = RegistrationException.class)
    public void registrationFailUserExist(){
        service.registrationUser(mapper.toDto(REGISTERED_USER));
    }

    @Test
    public void registrationUser(){
        service.registrationUser(NEW_USER);
    }
}
