package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import java.util.ArrayList;
import java.util.List;

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

    @InjectMocks
    private IssueController issueController;

    @Mock
    private IssueService issueService;

    @Test
    public void testGetIssue() {
        IssueDto issue = new IssueDto();
        issue.setTitle(TEST_TITLE);
        issue.setDescription(TEST_DESCRIPTION);

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
    public void testGetAllByPage() {
        Page<IssueDto> issueDtoPage = new PageImpl<>(new ArrayList<>());
        when(issueService.findAllByPage(any(Pageable.class))).thenReturn(issueDtoPage);

        Page<IssueDto> allByPageResult = issueController.getAllByPage(PAGE_INDEX, PAGE_SIZE);

        verify(issueService).findAllByPage(any(Pageable.class));
        verifyNoMoreInteractions(issueService);

        assertEquals(issueDtoPage, allByPageResult);
    }

    @Test
    public void testAddIssue() {
        IssueDto issueDto = new IssueDto();
        issueDto.setId(1L);
        issueDto.setTitle(TEST_TITLE);
        issueDto.setDescription(TEST_DESCRIPTION);
        when(issueService.addIssue(issueDto)).thenReturn(issueDto);

        IssueDto issueResult = issueController.addIssue(issueDto);

        assertEquals(TEST_TITLE, issueResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueResult.getDescription());
    }

    @Test
    public void testEditIssue() {
        IssueDto issue = new IssueDto();
        issue.setId(1L);
        issue.setTitle(TEST_TITLE);
        issue.setDescription(TEST_DESCRIPTION);
        when(issueService.editIssue(issue)).thenReturn(issue);

        IssueDto issueResult = issueController.editIssue(issue);

        assertEquals(TEST_TITLE, issueResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueResult.getDescription());
    }

    @Test
    public void testEditIssueNotFound() {
        IssueDto issue = new IssueDto();
        issue.setId(1L);
        when(issueService.editIssue(issue)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            issueController.editIssue(issue);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

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
