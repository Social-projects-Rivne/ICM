package ua.softserve.rv_028.issuecitymonitor.service;

import org.hibernate.mapping.Collection;
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
import ua.softserve.rv_028.issuecitymonitor.service.mappers.IssueMapper;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest {

    private final static String TEST_TITLE = "test";
    private final static String TEST_DESCRIPTION = "testDescription";
    private final static IllegalArgumentException EXCEPTION_NOT_FOUND = new IllegalArgumentException("issue not found");
    private final static int PAGE_INDEX = 1;
    private final static int PAGE_SIZE = 10;

    private Issue issue;
    private User user;

    @InjectMocks
    private IssueService issueService;

    @Mock
    private IssueMapper issueMapper;

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

        IssueDto issueResult = issueService.findById(1);
        assertEquals(TEST_TITLE, issueResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueResult.getDescription());
    }

    @Test
    public void testFindOneNotFound() {
        when(issueDao.findOne(-1L)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            issueService.findById(-1);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testGetAllByPage() {
        ArrayList<Issue> issues = new ArrayList<>();
        issues.add(issue);
        Page<Issue> issuePage = new PageImpl<>(issues);
        when(issueDao.findAll(any(Pageable.class))).thenReturn(issuePage);

        Page<IssueDto> allByPageResult = issueService.findAllByPage(PAGE_INDEX, PAGE_SIZE);

        verify(issueDao).findAll(any(Pageable.class));
        verifyNoMoreInteractions(issueDao);

        assertEquals(issuePage.getContent().get(0).getTitle(), allByPageResult.getContent().get(0).getTitle());
    }


    @Test
    public void testAddIssue() {
        IssueDto issueDto = new IssueDto();
        issueDto.setTitle(TEST_TITLE);
        issueDto.setDescription(TEST_DESCRIPTION);
        when(issueMapper.toDto(issueDao.save(issue))).thenReturn(issueDto);

        IssueDto issueDtoResult = issueService.addIssue(issueDto);
        assertEquals(TEST_TITLE, issueDtoResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueDtoResult.getDescription());
    }

    @Test
    public void testEditIssue() {
        IssueDto issueDto = new IssueDto();
        issueDto.setTitle(TEST_TITLE);
        issueDto.setDescription(TEST_DESCRIPTION);
        when(issueMapper.toDto(issueDao.save(issue))).thenReturn(issueDto);

        IssueDto issueDtoResult = issueService.update(issueDto);
        assertEquals(TEST_TITLE, issueDtoResult.getTitle());
        assertEquals(TEST_DESCRIPTION, issueDtoResult.getDescription());
    }

    @Test
    public void testEditIssueNotFound() {
        Issue issue = issueMapper.toEntity(issueService.findById(1L));
        IssueDto issueDto = new IssueDto();
        issueDto.setId(1L);
        when(issueMapper.toDto(issueDao.save(issue))).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            issueService.update(issueDto);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testDeleteIssueNotFound() {
        doThrow(EXCEPTION_NOT_FOUND).when(issueDao).delete(1L);

        try {
            issueService.deleteById(1);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }
}
