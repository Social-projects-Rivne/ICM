package ua.softserve.rv_028.issuecitymonitor.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createAdmin;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createUsersList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerITest {

    private static final int LIST_SIZE = 5;
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_OFFSET = 1;

    private User user, admin;
    private List<User> users;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserController controller;

    private static User USER = TestUtils.createAdmin(0);
    private static final String USERNAME = "mock-test@mail.com";

    @Before
    public void setup() {
        users = userDao.save(createUsersList(LIST_SIZE));
        admin = userDao.save(createAdmin(LIST_SIZE + 1));
        user = users.get(0);

        USER.setUsername(USERNAME);
        User user = userDao.findUserByUsername(USERNAME);
        if (user == null)
            userDao.save(USER);
        else
            USER = user;
        USER.setUserRole(UserRole.ADMIN);
        userDao.save(USER);
    }

    @After
    public void tearDown() {
        userDao.delete(users);
        userDao.delete(admin);
    }

    @Test
    public void testGetUser(){
        ResponseEntity<UserDto> responseEntity = testRestTemplate.
                getForEntity("/api/users/" + user.getId(), UserDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        UserDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(user.getFirstName(), responseObject.getFirstName());
        assertEquals(user.getUserStatus(), responseObject.getUserStatus());
    }

    @Test
    public void testGetAllByPage(){
        ResponseEntity<String> responseEntity = testRestTemplate.
                getForEntity("/api/users?size=" + PAGE_SIZE + "&page=" + PAGE_OFFSET, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode content = objectMapper.readTree(responseEntity.getBody()).path("content");
            assertEquals(PAGE_SIZE, content.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void testEditUser(){
        String updatedName = "testUpdateName";
        UserStatus updatedStatus = UserStatus.ACTIVE;
        UserDto userDto = userMapper.toDto(admin);
        userDto.setFirstName(updatedName);
        userDto.setUserStatus(updatedStatus);

        UserDto updatedUser = controller.update(userDto);

        assertEquals(updatedName, updatedUser.getFirstName());
        assertEquals(updatedStatus, updatedUser.getUserStatus());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void testDeleteUser(){
        long prevCount = userDao.count();
        controller.delete(user.getId());
        assertEquals(prevCount-1, userDao.count());
    }

}
