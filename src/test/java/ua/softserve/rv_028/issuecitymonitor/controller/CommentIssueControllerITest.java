package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentIssueControllerITest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testListComment(){
        ResponseEntity<String> responseEntity = testRestTemplate.
                getForEntity("/api/issues/comment", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
