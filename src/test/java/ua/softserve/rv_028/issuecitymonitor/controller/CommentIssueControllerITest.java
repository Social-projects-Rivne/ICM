package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.dto.CommentIssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.CommentIssue;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.CommentIssueMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentIssueControllerITest {

    private CommentIssue commentIssue;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CommentIssueMapper commentIssueMapper;

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
