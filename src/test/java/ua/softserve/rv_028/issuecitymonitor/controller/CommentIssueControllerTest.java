package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.rv_028.issuecitymonitor.dto.CommentIssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.service.CommentIssueService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentIssueControllerTest {

    private CommentIssueDto comment;
    private IssueDto issue;

    @InjectMocks
    private CommentIssueController commentIssueController;

    @Mock
    private CommentIssueService commentIssueService;

    @Test
    public void testListComment(Long id){
        List<CommentIssueDto> commentsList = new ArrayList<>();
        when(commentIssueService.findAllCommentById(id)).thenReturn(commentsList);

        List<CommentIssueDto> commentsResult = commentIssueController.listComment(id);

        assertEquals(commentsList, commentsResult);
    }

    @Test
    public void testCreateComment(){
        comment = new CommentIssueDto();
        comment.setBody("Description comment");
        when(commentIssueService.createComment(comment, issue)).thenReturn(comment);

        CommentIssueDto commentResult = commentIssueController.createComment(comment, issue);

        assertEquals("Description comment", commentResult.getBody());
    }
}
