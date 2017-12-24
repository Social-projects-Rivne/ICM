package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest {

    private final static String TEST_TITLE = "test";
    private final static String TEST_DESCRIPTION = "testDescription";
    private final static IllegalStateException EXCEPTION_NOT_FOUND = new IllegalStateException("issue not found");

    @InjectMocks
    private IssueService issueService;

    @Mock
    private IssueDao issueDao;

    @Test
    public void testFindOne() {
        Issue issue = new Issue();
        User user = new User();
        user.setId(1);
        issue.setUser(user);
        issue.setTitle(TEST_TITLE);
        issue.setDescription(TEST_DESCRIPTION);
        when(issueDao.findOne(1L)).thenReturn(issue);

        Issue issueResult = issueService.findOne(1);
        assertEquals(TEST_TITLE, issueResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueResult.getDescription());
    }

    @Test
    public void testFindOneNotFound() {
        when(issueDao.findOne(-1L)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            issueService.findOne(-1);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }
/*
    @Test
    public void testAddIssue() {
        IssueDto issueDto;
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setTitle(TEST_TITLE);
        issue.setDescription(TEST_DESCRIPTION);
        when(issueDto = new IssueDto(issueDao.save(issue))).thenReturn(issueDto);

        IssueDto issueDtoResult = issueService.addIssue(issueDto);
        assertEquals(TEST_TITLE, issueDtoResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueDtoResult.getDescription());
    }*/
}
