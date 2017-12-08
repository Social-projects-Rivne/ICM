package ua.softserve.rv_028.issuecitymonitor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.Role;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticatedUserTests {

    @Test
    public void testRightUser(){
        User user = createUser("email@mail.com", "pass", Role.ADMIN, UserStatus.ACTIVE);
        AuthenticatedUser authUser = new AuthenticatedUser(user);

        System.out.println(authUser);
        assertEquals(true, authUser.equals(user));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullEmail(){
        User user = createUser(null, "pass", Role.ADMIN, UserStatus.ACTIVE);
        new AuthenticatedUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyEmail(){
        User user = createUser("", "pass", Role.ADMIN, UserStatus.ACTIVE);
        new AuthenticatedUser(user);
    }

    @Test()
    public void testEmptyPass(){
        User user = createUser("email@mail.com", "", Role.ADMIN, UserStatus.ACTIVE);
        new AuthenticatedUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPass(){
        User user = createUser("email@mail.com", null, Role.ADMIN, UserStatus.ACTIVE);
        new AuthenticatedUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRole(){
        User user = createUser("email@mail.com", "pass", null, UserStatus.ACTIVE);
        new AuthenticatedUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullStatus(){
        User user = createUser("email@mail.com", "pass", Role.ADMIN, null);
        new AuthenticatedUser(user);
    }

    private User createUser(String email, String pass, Role role, UserStatus status){
        User user = new User();
        user.setEmail(email);
        user.setPassword(pass);
        user.setRole(role);
        user.setUserStatus(status);

        return user;
    }
}