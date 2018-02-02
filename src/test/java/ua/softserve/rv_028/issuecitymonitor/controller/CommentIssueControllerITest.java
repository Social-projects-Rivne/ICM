package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.dao.CommentIssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.CommentIssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.CommentIssue;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.CommentIssueMapper;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createCommentIssuesList;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createIssue;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentIssueControllerITest {

    private CommentIssue commentIssue;
    private List<CommentIssue> commentIssues;
    private Issue issue;
    private User user;

    @Autowired
    private CommentIssueDao commentIssueDao;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CommentIssueMapper commentIssueMapper;

    @Before
    public void setup(){
        user = userDao.save(createUser(0));
        issue = issueDao.save(createIssue(user, 0));
        commentIssues = commentIssueDao.save(createCommentIssuesList(issue, user));
        /*issues = issueDao.save(createIssuesList(user, LIST_SIZE));
        issue = issues.get(0);*/
    }

    @Test
    public void testListComment(){
        ResponseEntity<String> responseEntity = testRestTemplate.
                getForEntity("/api/issues/comment", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateComment(){
        String bodyComment = "Description comment";
        CommentIssueDto commentIssueDto = commentIssueMapper.toDto(commentIssue);
        commentIssueDto.setBody(bodyComment);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<CommentIssueDto> httpEntity = new HttpEntity<>(commentIssueDto,httpHeaders);
        ResponseEntity<CommentIssueDto> responseEntity = testRestTemplate.exchange
                ("/api/issues/comment" + commentIssueDto.getId(),
                HttpMethod.PUT, httpEntity, CommentIssueDto.class);

        System.out.println(httpEntity.toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CommentIssueDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(bodyComment, responseObject.getBody());
    }
}
