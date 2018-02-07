package ua.softserve.rv_028.issuecitymonitor.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MapContrrollerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Value("${value.from.file}")
    private String testData;

    @Test
    public void TestGetPath(){
        ResponseEntity<String> responseEntity;
        responseEntity = testRestTemplate.getForEntity("/api/map/img", String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        String responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(testData, responseObject);

    }
}
