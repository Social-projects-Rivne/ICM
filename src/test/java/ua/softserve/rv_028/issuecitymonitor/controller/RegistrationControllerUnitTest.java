package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.RegistrationService;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerUnitTest {

    @InjectMocks
    RegistrationController controller;


    @Test
    public void testRegistrationUserSuccessfully(){
        UserDto user = new UserDto();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setEmail("user@email.tt");
        user.setPassword("userPassPass");

        assertEquals(true, controller.userRegistration(user));
    }

    @Test
    public void testRegistrationUserFailed(){
        UserDto user = new UserDto();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setEmail("user@email.tt");
        user.setPassword("");

        assertEquals(false, controller.userRegistration(user));
    }

    @Test
    public void testRegistrationUserFailed2(){
        UserDto user = new UserDto();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setEmail("");
        user.setPassword("userPassPass");

        assertEquals(false, controller.userRegistration(user));
    }

    @Test
    public void testRegistrationUserFailed3(){
        UserDto user = new UserDto();
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("");
        user.setPassword("");

        assertEquals(false, controller.userRegistration(user));
    }

    @Test
    public void testRegistrationUserFailedNullFields(){
        UserDto user = new UserDto();
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail(null);
        user.setPassword(null);

        assertEquals(false, controller.userRegistration(user));
    }


}
