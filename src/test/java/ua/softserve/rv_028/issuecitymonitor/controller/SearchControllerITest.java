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
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.EventMapper;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.IssueMapper;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchControllerITest {

    private static final int PAGE_OFFSET = 1;
    private static final int PAGE_SIZE = 5;

    private static final String SEARCH_TITLE = "Title0";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private IssueMapper issueMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private UserDao userDao;

    private List<Event> events;
    private List<Issue> issues;
    private List<User> users;

    @Before
    public void setUp() {
        users = userDao.save(TestUtils.createUsersList(10));
        events = eventDao.save(TestUtils.createEventsList(users.get(0), 10));
        issues = issueDao.save(TestUtils.createIssuesList(users.get(0), 10));
    }

    @After
    public void tearDown() {
        issueDao.delete(issues);
        eventDao.delete(events);
        userDao.delete(users);
    }

    @Test
    public void testFindEventsByCriteria() {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/api/search/events?size="+
                PAGE_SIZE+"&page="+PAGE_OFFSET+"&text="+SEARCH_TITLE, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode content = objectMapper.readTree(responseEntity.getBody()).path("content");

            assertEquals(1, content.size());
            assertEquals(SEARCH_TITLE, content.findPath("title").textValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
