package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IssueControllerTest {

    private final static String TEST_TITLE = "test";
    private final static String TEST_DESCRIPTION = "testDescription";
    private final static IllegalStateException EXCEPTION_NOT_FOUND = new IllegalStateException("issue not found");
    private final static int PAGE_INDEX = 0;
    private final static int PAGE_SIZE = 10;

    private IssueDto issue;

    @InjectMocks
    private IssueController issueController;

    @Mock
    private IssueService issueService;

    @Before
    public void createIssueDto(){
        issue = new IssueDto();
        issue.setTitle(TEST_TITLE);
        issue.setDescription(TEST_DESCRIPTION);
    }

    @Test
    public void testGetIssue() {
        when(issueService.findById(1)).thenReturn(issue);

        IssueDto issueResult = issueController.getOne(1);

        assertEquals(TEST_TITLE, issueResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueResult.getDescription());
    }

    @Test
    public void testGetIssueNotFound() {
        when(issueService.findById(-1)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            issueController.getOne(-1);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testGetAllByPage(){
        Page<IssueDto> issueDtoPage = new PageImpl<>(new ArrayList<>());
        when(issueService.findAllByPage(any(Pageable.class))).thenReturn(issueDtoPage);

        Page<IssueDto> page = issueController.getAllByPage(PAGE_INDEX, PAGE_SIZE);

        verify(issueService).findAllByPage(any(Pageable.class));
        verifyNoMoreInteractions(issueService);
        assertEquals(issueDtoPage, page);
    }

    @Test
    public void testAddIssue() {
        when(issueService.addIssue(issue)).thenReturn(issue);

        IssueDto issueResult = issueController.addIssue(issue);

        assertEquals(TEST_TITLE, issueResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueResult.getDescription());
    }

    @Test
    public void testEditIssue() {
        when(issueService.editIssue(issue)).thenReturn(issue);

        IssueDto issueResult = issueController.editIssue(issue);

        assertEquals(TEST_TITLE, issueResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueResult.getDescription());
    }

    @Test
    public void testEditIssueNotFound() {
        when(issueService.editIssue(issue)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            issueController.editIssue(issue);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    //TODO fix tyhis once delete is implemented
    @Test
    public void testDeleteIssue() {
        doNothing().when(issueService).deleteIssue(1); //This is obvious
        issueController.deleteIssue(1);
    }

    @Test
    public void testDeleteIssueNotFound() {
        doThrow(EXCEPTION_NOT_FOUND).when(issueService).deleteIssue(1);

        try {
            issueController.deleteIssue(1);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }
}
