package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest {

    private final static String TEST_TITLE = "test";
    private final static String TEST_DESCRIPTION = "testDescription";
    private final static IllegalStateException EXCEPTION_NOT_FOUND = new IllegalStateException("issue not found");

    private IssueDao issueDao;
    private IssueService issueService = new IssueService(issueDao);

    @Test
    public void testFindOne(){
        Issue issue = new Issue();
        issue.setTitle(TEST_TITLE);
        issue.setDescription(TEST_DESCRIPTION);

        when(issueService.findOne(1)).thenReturn(issue);

        Issue entity = issueService.findOne(1);

        assertEquals(TEST_TITLE, entity.getTitle());
        assertEquals(TEST_DESCRIPTION, entity.getDescription());
    }

    @Test
    public void testFindById(){
        IssueDto issueDto = new IssueDto();
        issueDto.setTitle(TEST_TITLE);
        issueDto.setDescription(TEST_DESCRIPTION);

        when(issueService.findById(1)).thenReturn(issueDto);

        IssueDto dto = issueService.findById(1);

        assertEquals(TEST_TITLE, dto.getTitle());
        assertEquals(TEST_DESCRIPTION, dto.getDescription());
    }

    @Test
    public void testFindByIdNotFound() {
        when(issueService.findById(-1)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            issueService.findById(-1);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testAddIssue(){
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setTitle(TEST_TITLE);
        issue.setDescription(TEST_DESCRIPTION);
        when(issueDao.save(issue)).thenReturn(issue);

        IssueDto added = issueService.addIssue(issue);

        assertEquals(TEST_TITLE,added.getTitle());
        assertEquals(TEST_DESCRIPTION,added.getDescription());
    }

    @Test
    public void testEditIssue(){
        IssueDto issueDto = new IssueDto();
        issueDto.setId(1L);
        issueDto.setTitle(TEST_TITLE);
        issueDto.setDescription(TEST_DESCRIPTION);
        when(issueService.editIssue(issueDto)).thenReturn(issueDto);

        IssueDto edited = issueController.editIssue(issueDto);

        assertEquals(TEST_TITLE,edited.getTitle());
        assertEquals(TEST_DESCRIPTION,edited.getDescription());
    }

    @Test
    public void testEditIssueNotFound(){
        IssueDto issueDto = new IssueDto();
        issueDto.setId(1L);
        when(issueService.editIssue(issueDto)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            issueController.editIssue(issueDto);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    //TODO fix tyhis once delete is implemented
    @Test
    public void testDeleteIssue(){
        doNothing().when(issueService).deleteIssue(1); //This is obvious
        issueController.deleteIssue(1);
    }

    @Test
    public void testDeleteIssueNotFound(){
        doThrow(EXCEPTION_NOT_FOUND).when(issueService).deleteIssue(1);

        try {
            issueController.deleteIssue(1);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    /*@Test
    public void testFindAllByPage(){
        Page<IssueDto> issueDtoPage = new PageImpl<>(new ArrayList<>());
        when(issueService.findAllByPage(any(Pageable.class))).thenReturn(issueDtoPage);

        Page<IssueDto> page = issueService.findAllByPage(any(Pageable.class));

        verify(issueService).findAllByPage(any(Pageable.class));
        verifyNoMoreInteractions(issueService);
        assertEquals(issueDtoPage, page);
    }*/
}
