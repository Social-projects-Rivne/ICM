package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.rv_028.issuecitymonitor.controller.UserController;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;
import ua.softserve.rv_028.issuecitymonitor.exception.UserNotFoundException;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UsersControllerUnitTest {
    private final static String TEST_NAME= "testName";
    private final static UserStatus TEST_STATUS = UserStatus.BANNED;
    private final static IllegalStateException EXCEPTION_NOT_FOUND = new IllegalStateException("user not found");

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;



    @Test
    public void testGetUser() throws UserNotFoundException {
        UserDto userDto = new UserDto();

        userDto.setFirstName(TEST_NAME);
        userDto.setUserStatus(TEST_STATUS);


        when(userService.findByID(1)).thenReturn(userDto);

        UserDto dto = userController.getOne(1);

        assertEquals(TEST_NAME,dto.getFirstName());
        assertEquals(TEST_STATUS,dto.getUserStatus());


    }

    @Test
    public void testGetUsersNotFound() throws UserNotFoundException {
        when(userService.findByID(-1)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            userController.getOne(-1);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }


    @Test
    public void testEditUser() throws UserNotFoundException {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName(TEST_NAME);
        userDto.setUserStatus(TEST_STATUS);
        when(userService.updateUser(userDto)).thenReturn(userDto);
    }

    @Test
    public void testEditUserNotFound() throws UserNotFoundException {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        when(userService.updateUser(userDto)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            userController.updateForUser(userDto);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {
        doNothing().when(userService).deleteById(1);
        userController.deleteUser(1);
    }

    @Test
    public void testDeleteUserNotFound() throws UserNotFoundException {
        doThrow(EXCEPTION_NOT_FOUND).when(userService).deleteById(1);

        try {
            userController.deleteUser(1);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }
}
