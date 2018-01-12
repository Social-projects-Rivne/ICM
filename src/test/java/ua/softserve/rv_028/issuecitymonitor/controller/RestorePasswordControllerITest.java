package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.RestorePasswordDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.RestorePassword;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.RestorePasswordService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestorePasswordControllerITest {

    private static User USER = TestUtils.createUser(0);
    private static final String PASSWORD = "000";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestorePasswordService service;

    @Autowired
    private RestorePasswordDao restorePassDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private TestRestTemplate rest;

    @Before
    public void setup(){
        User user = userDao.findUserByUsername(USER.getUsername());
        if (user == null)
            USER = userDao.save(USER);
        else
            USER = user;
    }

    @Test
    public void setNewPasswordForUser(){
        restorePassDao.deleteByUser(USER);
        service.createResetToken(USER.getUsername());
        String newPassword = "newPassword";
        String token = restorePassDao.findByUser(USER).getToken();

        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", USER.getUsername());
        map.add("password", newPassword);
        map.add("token", token);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Void> response = rest.postForEntity("/api/createNewPassword", request, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User updatedUser = userDao.findUserByUsername(USER.getUsername());
        assertEquals(true, encoder.matches(newPassword, updatedUser.getPassword()));
    }

    @Test
    public void setNewPasswordForUserFail(){
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", null);
        map.add("password", null);
        map.add("token", null);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Void> response = rest.postForEntity("/api/createNewPassword", request, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void setNewPasswordForUserFailToken(){
        service.createResetToken(USER.getUsername());
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", USER.getUsername());
        map.add("password", PASSWORD);
        map.add("token", "incorrectToken");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Void> response = rest.postForEntity("/api/createNewPassword", request, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void setNewPasswordForUserNoToken(){
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", USER.getUsername());
        map.add("password", PASSWORD);
        map.add("token", "incorrectToken");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Void> response = rest.postForEntity("/api/createNewPassword", request, Void.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void createResetToken(){
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", USER.getUsername());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Void> response = rest.postForEntity("/api/createResetToken", request, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(USER.getId());

        assertNotNull(restorePassDao.findByUser(USER));
    }

    @After
    public void cleanup(){
        restorePassDao.deleteAll();
    }
}
