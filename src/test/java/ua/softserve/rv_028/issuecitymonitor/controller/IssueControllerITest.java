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
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
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

    private static User USER = TestUtils.createAdmin(0);
    private static final String USERNAME = "mock-test@mail.com";

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private IssueMapper issueMapper;

    @Autowired
    private IssueController controller;

    @Before
    public void setup(){
        user = userDao.save(createUser(0));
        issues = issueDao.save(createIssuesList(user, LIST_SIZE));
        issue = issues.get(0);

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

    @Test(expected = RuntimeException.class)
    @WithMockUser(username = "mock-test@mail.com")
    public void testAddIssue(){
        String addTitle = "testAddTitle";
        String addDescription = "testAddDescription";
        IssueDto issueDto = issueMapper.toDto(issue);

        IssueDto newIssue = controller.addIssue(issueDto);

        assertEquals(addTitle, newIssue.getTitle());
        assertEquals(addDescription, newIssue.getDescription());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void testEditIssue(){
        String updatedTitle = "testUpdateTitle";
        String updatedDescription = "testUpdateDescription";
        IssueDto issueDto = issueMapper.toDto(issue);
        issueDto.setTitle(updatedTitle);
        issueDto.setDescription(updatedDescription);

        IssueDto updatedIssues = controller.editIssue(issueDto);


        assertEquals(updatedTitle, updatedIssues.getTitle());
        assertEquals(updatedDescription, updatedIssues.getDescription());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void testDeleteIssue(){
        long prevCount = issueDao.count();
        controller.deleteIssue(issue.getId());
        assertEquals(prevCount-1, issueDao.count());
    }

    @Test
    public void testIssueNotFound(){
        ResponseEntity<IssueDto> responseEntity = testRestTemplate.
                getForEntity("/api/issues/-1", IssueDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
