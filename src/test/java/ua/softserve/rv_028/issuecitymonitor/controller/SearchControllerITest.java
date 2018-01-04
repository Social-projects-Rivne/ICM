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
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchControllerITest {

    private static final int PAGE_OFFSET = 1;
    private static final int PAGE_SIZE = 5;

    private static final String EXPECTED_SEARCH_TITLE = TITLE + 0;
    private static final String EXPECTED_SEARCH_DESCRIPTION = DESCRIPTION + 0;
    private static final String EXPECTED_SEARCH_NAME = USER_FNAME + 0;
    private static final String EXPECTED_SEARCH_EMAIL = USER_EMAIL + 0;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private PetitionDao petitionDao;

    @Autowired
    private UserDao userDao;

    private List<Event> events;
    private List<Issue> issues;
    private List<User> users;
    private List<Petition> petitions;

    @Before
    public void setUp() {
        users = userDao.save(createUsersList(10));
        events = eventDao.save(createEventsList(users.get(0), 10));
        issues = issueDao.save(createIssuesList(users.get(0), 10));
        petitions = petitionDao.save(createPetitionsList(users.get(0), 10));
    }

    @After
    public void tearDown() {
        petitionDao.delete(petitions);
        issueDao.delete(issues);
        eventDao.delete(events);
        userDao.delete(users);
    }

    @Test
    public void testFindEventsByCriteria_expectSizeOneAndEquality() {
        testSearchCriteria("/api/search/events?size="+ PAGE_SIZE +"&page=" + PAGE_OFFSET + "&text=" + EXPECTED_SEARCH_TITLE,
                "title", EXPECTED_SEARCH_TITLE);
        testSearchCriteria("/api/search/events?size="+ PAGE_SIZE +"&page=" + PAGE_OFFSET + "&text=" + EXPECTED_SEARCH_DESCRIPTION,
                "description", EXPECTED_SEARCH_DESCRIPTION);
    }

    @Test
    public void testFindIssuesByCriteria_expectSizeOneAndEquality() {
        testSearchCriteria("/api/search/issues?size="+ PAGE_SIZE +"&page=" + PAGE_OFFSET + "&text=" + EXPECTED_SEARCH_TITLE,
                "title", EXPECTED_SEARCH_TITLE);
        testSearchCriteria("/api/search/issues?size="+ PAGE_SIZE +"&page=" + PAGE_OFFSET + "&text=" + EXPECTED_SEARCH_DESCRIPTION,
                "description", EXPECTED_SEARCH_DESCRIPTION);
    }

    @Test
    public void testFindPetitionsByCriteria_expectSizeOneAndEquality() {
        testSearchCriteria("/api/search/petitions?size="+ PAGE_SIZE +"&page=" + PAGE_OFFSET + "&text=" + EXPECTED_SEARCH_TITLE,
                "title", EXPECTED_SEARCH_TITLE);
        testSearchCriteria("/api/search/petitions?size="+ PAGE_SIZE +"&page=" + PAGE_OFFSET + "&text=" + EXPECTED_SEARCH_DESCRIPTION,
                "description", EXPECTED_SEARCH_DESCRIPTION);
    }

    @Test
    public void testFindUsersByCriteria_expectSizeOneAndEquality() {
        testSearchCriteria("/api/search/users?size="+ PAGE_SIZE +"&page=" + PAGE_OFFSET + "&name=" + EXPECTED_SEARCH_NAME,
                "firstName", EXPECTED_SEARCH_NAME);
        testSearchCriteria("/api/search/users?size="+ PAGE_SIZE +"&page=" + PAGE_OFFSET + "&email=" + EXPECTED_SEARCH_EMAIL,
                "email", EXPECTED_SEARCH_EMAIL);
    }

    private void testSearchCriteria(String url, String valuePath, String expectedValue) {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode content = objectMapper.readTree(responseEntity.getBody()).path("content");

            assertEquals(1, content.size());
            assertEquals(expectedValue, content.findPath(valuePath).textValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
