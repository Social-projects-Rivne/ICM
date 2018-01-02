/*
package ua.softserve.rv_028.issuecitymonitor.service;
//TODO all this
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest {

    private final static String TEST_TITLE = "test";
    private final static String TEST_DESCRIPTION = "testDescription";
    private final static IllegalStateException EXCEPTION_NOT_FOUND = new IllegalStateException("issue not found");

    private Issue issue;
    private User user;

    @InjectMocks
    private IssueService issueService;

    @Mock
    private MapperService mapperService;

    @Mock
    private IssueDao issueDao;

    @Before
    public void createIssueDto(){
        issue = new Issue();
        user = new User();
        user.setId(1);
        issue.setUser(user);
        issue.setTitle(TEST_TITLE);
        issue.setDescription(TEST_DESCRIPTION);
    }

    @Test
    public void testFindOne() {
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

    @Test
    public void testGetAllByPage() {
        Page<IssueDto> issueDtoPage = new PageImpl<>(new ArrayList<>());
        when(issueDao.findAll(any(Pageable.class)).map(mapperService::fromEntityToDto))
                .thenReturn(issueDtoPage);

        Page<IssueDto> allByPageResult = issueService.findAllByPage(any(Pageable.class));

        verify(issueDao).findAll(any(Pageable.class));
        verifyNoMoreInteractions(issueDao);

        assertEquals(issueDtoPage, allByPageResult);
    }

    @Test
    public void testAddIssue() {
        IssueDto issueDto = new IssueDto();
        issueDto.setTitle(TEST_TITLE);
        issueDto.setDescription(TEST_DESCRIPTION);
        when(mapperService.fromEntityToDto(issueDao.save(issue))).thenReturn(issueDto);

        IssueDto issueDtoResult = issueService.addIssue(issueDto);
        assertEquals(TEST_TITLE, issueDtoResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueDtoResult.getDescription());
    }

    @Test
    public void testEditIssue() {
        IssueDto issueDto = new IssueDto();
        issueDto.setTitle(TEST_TITLE);
        issueDto.setDescription(TEST_DESCRIPTION);
        when(mapperService.fromEntityToDto(issueDao.save(issue))).thenReturn(issueDto);

        IssueDto issueDtoResult = issueService.update(issueDto);
        assertEquals(TEST_TITLE, issueDtoResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueDtoResult.getDescription());
    }

    @Test
    public void testEditIssueNotFound() {
        Issue issue = issueService.findOne(1L);
        IssueDto issueDto = new IssueDto();
        issueDto.setId(1L);
        when(mapperService.fromEntityToDto(issueDao.save(issue))).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            issueService.update(issueDto);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testDeleteIssue() {
        doNothing().when(issueDao).delete(1L);
        issueService.deleteById(1);
    }

    @Test
    public void testDeleteIssueNotFound() {
        doThrow(EXCEPTION_NOT_FOUND).when(issueDao).delete(1L);

        try {
            issueService.deleteById(1);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }
}
*/
