package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserve.rv_028.issuecitymonitor.IssueCityMonitorApplication;
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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IssueCityMonitorApplication.class)
public class SearchControllerITest {

    private static final int PAGE_OFFSET = 1;
    private static final int PAGE_SIZE = 5;

    private static final PageRequest PAGE_REQUEST = new PageRequest(PAGE_OFFSET,PAGE_SIZE);

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

    @Test
    public void testFindEventsByCriteria() {

        //TODO

    }
}
