package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private final static String TEST_NAME= "testName";
    private final static UserStatus TEST_STATUS = UserStatus.BANNED;
    private final static IllegalArgumentException EXCEPTION_NOT_FOUND = new IllegalArgumentException("user not found");
    private final static int PAGE_INDEX = 1;
    private final static int PAGE_SIZE = 10;
    private final static Sort.Direction SORT_DIRECTION = ASC;
    private final static String SORT_COLUMN = "id";

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void testGetUser() {
        UserDto userDto = new UserDto();

        userDto.setFirstName(TEST_NAME);
        userDto.setUserStatus(TEST_STATUS);

        when(userService.findById(1)).thenReturn(userDto);

        UserDto dto = userController.getOne(1);

        assertEquals(TEST_NAME,dto.getFirstName());
        assertEquals(TEST_STATUS,dto.getUserStatus());
    }

    @Test
    public void testGetUsersNotFound() {
        when(userService.findById(-1)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            userController.getOne(-1);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testGetAllByPage(){
        Page<UserDto> userDtoPage = new PageImpl<>(new ArrayList<>());
        when(userService.findAllByPage(PAGE_INDEX, PAGE_SIZE, SORT_DIRECTION, SORT_COLUMN, false))
                .thenReturn(userDtoPage);

        Page<UserDto> page = userController.getAllByPage(PAGE_INDEX, PAGE_SIZE, SORT_DIRECTION, SORT_COLUMN, false);
        verify(userService).findAllByPage(PAGE_INDEX, PAGE_SIZE, SORT_DIRECTION, SORT_COLUMN, false);
        verifyNoMoreInteractions(userService);
        assertEquals(userDtoPage, page);
    }

    @Test
    public void testEditUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName(TEST_NAME);
        userDto.setUserStatus(TEST_STATUS);
        when(userService.update(userDto)).thenReturn(userDto);
    }

    @Test
    public void testEditUserNotFound() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        when(userService.update(userDto)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            userController.update(userDto);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testDeleteUserNotFound() {
        doThrow(EXCEPTION_NOT_FOUND).when(userService).deleteById(1);

        try {
            userController.delete(1);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }
}
