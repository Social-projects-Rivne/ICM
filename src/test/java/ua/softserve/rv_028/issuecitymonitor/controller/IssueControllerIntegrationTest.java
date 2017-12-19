package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.IssueMapper;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.MapperService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueControllerIntegrationTest {

    private Issue issue;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private IssueMapper mapperService;

    @Before
    public void setup(){
        issue = issueDao.findAllByOrderByIdAsc().get(1);
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
    public void testAddIssue(){
        String addTitle = "testAddTitle";
        String addDescription = "testAddDescription";
        IssueDto issueDto = mapperService.toDto(issue);
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
        IssueDto issueDto = mapperService.toDto(issue);
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
