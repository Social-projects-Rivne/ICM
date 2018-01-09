package ua.softserve.rv_028.issuecitymonitor.service;

import org.junit.After;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationServiceTest {

    private static User REGISTERED_USER;
    private static UserDto NEW_USER;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RegistrationService registrationService;

    @Before
    public void setup(){
        REGISTERED_USER = userDao.save(TestUtils.createUser(0));
        NEW_USER = TestUtils.createUserDto(1);
    }

    @Test
    public void userExist(){
        boolean userExist = registrationService.isPossibleRegistration(REGISTERED_USER.getUsername());
        assertEquals(true, userExist);
    }

    @Test
    public void userExistFalse(){
        boolean userExist = registrationService.isPossibleRegistration("no-email");
        assertEquals(false, userExist);
    }

    @Test(expected = RegistrationException.class)
    public void registrationFailUserExist(){
        registrationService.registrationUser(mapper.toDto(REGISTERED_USER));
    }

    @Test
    public void registrationUser(){
        registrationService.registrationUser(NEW_USER);
    }

    @After
    public void cleanup(){
        userDao.deleteAll();
    }
}
