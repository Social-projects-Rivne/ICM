package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.UserProfileService;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class AuthenticatedUsersControllerTest {

    private static final String ANONYMOUS_USER_NAME = "anonymous";
    private static final String MOCK_USER_NAME = "user";
    private static final String MOCK_USER_PASSWORD = "password";
    private static final Collection<? extends GrantedAuthority> MOCK_USER_AUTHORITY =
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    private static final IllegalArgumentException USER_NOT_FOUND = new IllegalArgumentException("User not found");
    private Map<String, Object> userInfo;

    @InjectMocks
    private AuthenticatedUsersController controller;

    @Mock
    private UserProfileService profileService;

    @Before
    public void setup(){
        userInfo = new HashMap<>();
        userInfo.put("email", MOCK_USER_NAME);
        userInfo.put("authorities", MOCK_USER_AUTHORITY);
    }

    @Test
    @WithMockUser
    public void getUserName(){
        assertEquals(controller.getUserName(), MOCK_USER_NAME);
    }

    @Test
    @WithAnonymousUser
    public void getAnonymousUserName(){
        assertEquals(controller.getUserName(), ANONYMOUS_USER_NAME);
    }

    @Test
    @WithMockUser
    public void getUserInfo() {
        when(profileService.getUserInfo(MOCK_USER_NAME)).thenReturn(userInfo);
        assertEquals(controller.getUserInfo(), userInfo);
    }

    @Test(expected = IllegalArgumentException.class)
    @WithAnonymousUser
    public void getUserInfoFail(){
        when(profileService.getUserInfo(ANONYMOUS_USER_NAME)).thenThrow(USER_NOT_FOUND);
        controller.getUserInfo();
    }

    @Test
    @WithMockUser
    public void getUserAuthority(){
        assertArrayEquals(controller.getUserAuthority().toArray(), MOCK_USER_AUTHORITY.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    @WithAnonymousUser
    public void updatePasswordFail(){
        when(profileService.updatePassword(ANONYMOUS_USER_NAME, "password", "password"))
                .thenThrow(USER_NOT_FOUND);
        controller.updatePassword("password", "password");
    }
}
