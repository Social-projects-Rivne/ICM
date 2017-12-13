package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.RegistrationService;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerUnitTest {

    @InjectMocks
    private RegistrationController controller;


    @Test
    public void testRegistrationUserSuccessfully(){
        UserDto user = new UserDto();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setEmail("user@email.tt");
        user.setPassword("userPassPass");

        assertEquals(HttpStatus.CREATED, controller.userRegistration(user));
    }

    @Test
    public void testRegistrationUserEmptyPassword(){
        UserDto user = new UserDto();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setEmail("user@email.tt");
        user.setPassword("");

        assertEquals(HttpStatus.BAD_REQUEST, controller.userRegistration(user));
    }

    @Test
    public void testRegistrationUserEmptyEmail(){
        UserDto user = new UserDto();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setEmail("");
        user.setPassword("userPassPass");

        assertEquals(HttpStatus.BAD_REQUEST, controller.userRegistration(user));
    }

    @Test
    public void testRegistrationUserEmptyAllFields(){
        UserDto user = new UserDto();
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("");
        user.setPassword("");

        assertEquals(HttpStatus.BAD_REQUEST, controller.userRegistration(user));
    }

    @Test
    public void testRegistrationUserFailedNullFields(){
        UserDto user = new UserDto();
        user.setFirstName(null);
        user.setLastName(null);
        user.setEmail(null);
        user.setPassword(null);

        assertEquals(HttpStatus.BAD_REQUEST, controller.userRegistration(user));
    }


}
