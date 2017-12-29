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
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestApplication.class)
public class UserControllerIntegrationTest {

    private static final int LIST_SIZE = 5;
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_OFFSET = 1;

    private User user;
    private List<User> users;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup(){
        users = userDao.save(TestUtils.createUsersList(LIST_SIZE));
        user = users.get(0);
    }

    @After
    public void tearDown() {
        userDao.delete(users);
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
    public void testEditUser(){
        String updatedName = "testUpdateName";
        UserStatus updatedStatus = UserStatus.ACTIVE;
        UserDto userDto = new UserDto(user);
        userDto.setFirstName(updatedName);
        userDto.setUserStatus(updatedStatus);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<UserDto> httpEntity = new HttpEntity<>(userDto,httpHeaders);
        ResponseEntity<UserDto> responseEntity = testRestTemplate.exchange("/api/users/" + userDto.getId(),
                HttpMethod.PUT, httpEntity, UserDto.class);

        System.out.println(httpEntity.toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(updatedName, responseObject.getFirstName());
        assertEquals(updatedStatus, responseObject.getUserStatus());
    }

    @Test
    public void testDeleteUser(){
        long prevCount = userDao.count();
        testRestTemplate.delete("/api/users/delete/" + user.getId());
        assertEquals(prevCount-1, userDao.count());
    }

}
