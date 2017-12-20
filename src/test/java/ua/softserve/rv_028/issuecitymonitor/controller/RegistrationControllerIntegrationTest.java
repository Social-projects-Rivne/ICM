package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.IssueCityMonitorApplication;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IssueCityMonitorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerIntegrationTest {

    private User userInDB;
    private UserDto newUser;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup(){
        Random random = new Random();
        userInDB = userDao.findAll().get(0);
        newUser =  new UserDto("fN", "lN", "test.icm.user." +random.nextInt()+"@world.com", "pass");
    }

    @Test
    public void registrationFailUserExist(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> request = new HttpEntity<>(userMapper.toDto(userInDB), headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity( "/api/registration", request, String.class);
        System.out.println(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, HttpStatus.valueOf(response.getStatusCodeValue()));
    }

    @Test
    public void registrationUser(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> request = new HttpEntity<>(newUser, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity( "/api/registration", request, String.class);
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(response.getStatusCodeValue()));

        User registerUser = userDao.findUserByUsername(newUser.getEmail());
        assertNotEquals(null, registerUser);
    }


}
