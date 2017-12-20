package ua.softserve.rv_028.issuecitymonitor.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.IssueCityMonitorApplication;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.exception.RegistrationException;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IssueCityMonitorApplication.class)
public class RegistrationServiceTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RegistrationService registrationService;

    private User user;

    @Before
    public void setup(){
        user = userDao.findAll().get(0);
    }

    @Test
    public void userExistTrue(){
        boolean userExist = registrationService.isPossibleRegistration(user.getUsername());
        assertEquals(true, userExist);
    }

    @Test
    public void userExistFalse(){
        boolean userExist = registrationService.isPossibleRegistration("no-email");
        assertEquals(false, userExist);
    }

    @Test(expected = RegistrationException.class)
    public void registrationFailUserExist(){
        registrationService.registrationUser(mapper.toDto(user));
    }
}
