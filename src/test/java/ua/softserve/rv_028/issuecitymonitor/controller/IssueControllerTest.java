package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueLocationDto;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RunWith(MockitoJUnitRunner.class)
public class IssueControllerTest {

    private final static String TEST_TITLE = "test";
    private final static String TEST_DESCRIPTION = "testDescription";
    private final static IllegalArgumentException EXCEPTION_NOT_FOUND = new IllegalArgumentException("issue not found");
    private final static int PAGE_INDEX = 1;
    private final static int PAGE_SIZE = 10;
    private final static Sort.Direction SORT_DIRECTION = ASC;
    private final static String SORT_COLUMN = "id";

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
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testGetAllByPage(){
        Page<IssueDto> issueDtoPage = new PageImpl<>(new ArrayList<>());
        when(issueService.findAllByPage(PAGE_INDEX, PAGE_SIZE, SORT_DIRECTION, SORT_COLUMN)).thenReturn(issueDtoPage);
        Page<IssueDto> page = issueController.getAllByPage(PAGE_INDEX, PAGE_SIZE, SORT_DIRECTION, SORT_COLUMN);

        verify(issueService).findAllByPage(PAGE_INDEX, PAGE_SIZE, SORT_DIRECTION, SORT_COLUMN);
        verifyNoMoreInteractions(issueService);
        assertEquals(issueDtoPage, page);
    }

    @Test
    public void testGetAll(){
        List<IssueLocationDto> issueDto = new ArrayList<>();
        when(issueService.findAll()).thenReturn(issueDto);

        List<IssueLocationDto>  issue = issueController.getAll();

        verify(issueService).findAll();
        assertEquals(issueDto, issue);
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
        when(issueService.update(issue)).thenReturn(issue);

        IssueDto issueResult = issueController.editIssue(issue);

        assertEquals(TEST_TITLE, issueResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueResult.getDescription());
    }

    @Test
    public void testEditIssueNotFound() {
        when(issueService.update(issue)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            issueController.editIssue(issue);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testDeleteIssueNotFound() {
        doThrow(EXCEPTION_NOT_FOUND).when(issueService).deleteById(1);

        try {
            issueController.deleteIssue(1);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }
}
