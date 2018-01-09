package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerITest {

    private User REGISTERED_USER;
    private UserDto NEW_USER;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup(){
        REGISTERED_USER = userDao.save(TestUtils.createUser(0));
        NEW_USER = TestUtils.createUserDto(1);
    }

    @Test
    public void registrationUser(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> request = new HttpEntity<>(NEW_USER, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity( "/api/registration", request, String.class);
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(response.getStatusCodeValue()));

        User registerUser = userDao.findUserByUsername(NEW_USER.getEmail());
        assertNotNull(registerUser);
    }

    @Test
    public void registrationFailUserExist(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> request = new HttpEntity<>(userMapper.toDto(REGISTERED_USER), headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity( "/api/registration", request, String.class);
        System.out.println(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, HttpStatus.valueOf(response.getStatusCodeValue()));
    }

    @Test
    public void checkEmail(){
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", NEW_USER.getEmail());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Boolean> response = testRestTemplate.postForEntity( "/api/checkEmail", request, Boolean.class);
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(response.getStatusCodeValue()));
        assertEquals(false, response.getBody());
    }

    @Test
    public void checkEmailNewUser(){
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", REGISTERED_USER.getUsername());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Boolean> response = testRestTemplate.postForEntity( "/api/checkEmail", request, Boolean.class);
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(response.getStatusCodeValue()));
        assertEquals(true, response.getBody());
    }

    @After
    public void cleanup(){
        userDao.deleteAll();
    }
}
