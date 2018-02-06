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
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.IssueMapper;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createIssuesList;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueControllerITest {

    private static final int LIST_SIZE = 5;
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_OFFSET = 1;

    private Issue issue;
    private List<Issue> issues;
    private User user;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private IssueMapper issueMapper;

    @Before
    public void setup(){
        user = userDao.save(createUser(0));
        issues = issueDao.save(createIssuesList(user, LIST_SIZE));
        issue = issues.get(0);
    }

    @After
    public void tearDown() {
        issueDao.delete(issues);
        userDao.delete(user);
    }

    @Test
    public void testGetIssue(){
        ResponseEntity<IssueDto> responseEntity = testRestTemplate.
                getForEntity("/api/issues/" + issue.getId(), IssueDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        IssueDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(issue.getTitle(), responseObject.getTitle());
        assertEquals(issue.getDescription(), responseObject.getDescription());
    }

    @Test
    public void testGetAllByPage(){
        ResponseEntity<String> responseEntity = testRestTemplate.
                getForEntity("/api/issues?size=" + PAGE_SIZE + "&page=" + PAGE_OFFSET, String.class);
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
    public void testGetAll(){
        ResponseEntity<String> responseEntity = testRestTemplate.
                getForEntity("/api/issues/map", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    public void testAddIssue(){
        String addTitle = "testAddTitle";
        String addDescription = "testAddDescription";
        IssueDto issueDto = issueMapper.toDto(issue);
        issueDto.setTitle(addTitle);
        issueDto.setDescription(addDescription);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<IssueDto> httpEntity = new HttpEntity<>(issueDto,httpHeaders);
        ResponseEntity<IssueDto> responseEntity = testRestTemplate.exchange("/api/issues/" + issueDto.getId(),
                HttpMethod.PUT, httpEntity, IssueDto.class);

        System.out.println(httpEntity.toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        IssueDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(addTitle, responseObject.getTitle());
        assertEquals(addDescription, responseObject.getDescription());
    }

    @Test
    public void testEditIssue(){
        String updatedTitle = "testUpdateTitle";
        String updatedDescription = "testUpdateDescription";
        IssueDto issueDto = issueMapper.toDto(issue);
        issueDto.setTitle(updatedTitle);
        issueDto.setDescription(updatedDescription);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<IssueDto> httpEntity = new HttpEntity<>(issueDto,httpHeaders);
        ResponseEntity<IssueDto> responseEntity = testRestTemplate.exchange("/api/issues/" + issueDto.getId(),
                HttpMethod.PUT, httpEntity, IssueDto.class);

        System.out.println(httpEntity.toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        IssueDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(updatedTitle, responseObject.getTitle());
        assertEquals(updatedDescription, responseObject.getDescription());
    }

    @Test
    public void testDeleteIssue(){
        long prevCount = issueDao.count();
        testRestTemplate.delete("/api/issues/" + issue.getId());
        assertEquals(prevCount-1, issueDao.count());
    }

    @Test
    public void testIssueNotFound(){
        ResponseEntity<IssueDto> responseEntity = testRestTemplate.
                getForEntity("/api/issues/-1", IssueDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
