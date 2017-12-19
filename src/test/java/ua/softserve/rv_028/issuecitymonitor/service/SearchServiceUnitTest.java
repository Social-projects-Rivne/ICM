package ua.softserve.rv_028.issuecitymonitor.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceUnitTest {

    @InjectMocks
    private SearchServiceImpl searchService;

    @Mock
    private UserDao userDao;

    @Mock
    private EventDao eventDao;

    @Mock
    private PetitionDao petitionDao;

    @Mock
    private IssueDao issueDao;

    @Mock
    private MapperService mapperService;

    private Map<String,String> queryMap;

    @Before
    public void setup() {
    }

    @Test
    public void testEventSearch() {
    }

}
