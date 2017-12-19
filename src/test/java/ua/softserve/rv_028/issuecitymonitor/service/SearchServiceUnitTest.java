package ua.softserve.rv_028.issuecitymonitor.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specifications;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.MapperService;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.EventSpecification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceUnitTest {

    private static final String TEXT_KEY = "text";
    private static final String TEXT_VALUE = "testValue";

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

    private Map<String,String> eventQueryMap;

    @Before
    public void setup() {
        eventQueryMap = Collections.singletonMap(TEXT_KEY,TEXT_VALUE);
    }

    @Test
    public void testEventSearch() {
        List<Event> events = new ArrayList<>();

        User user = new User();

        Event event1 = new Event();
        event1.setTitle("testText");
        event1.setUser(user);
        event1.setInitialDate(LocalDateTime.now());
        event1.setEndDate(LocalDateTime.now());
        events.add(event1);

        Event event2 = new Event();
        event2.setDescription("testText");
        event2.setUser(user);
        event2.setInitialDate(LocalDateTime.now());
        event2.setEndDate(LocalDateTime.now());
        events.add(event2);

        Specifications<Event> eventSpecifications = Specifications.where(new EventSpecification(TEXT_KEY, TEXT_VALUE));

        when(eventDao.findAll(eventSpecifications)).thenReturn(events);

        List<EventDto> resultList = searchService.findEventsByCriteria(eventQueryMap);

        assertEquals(event1.getTitle(), resultList.get(0).getTitle());
        assertEquals(event2.getDescription(), resultList.get(1).getDescription());
    }

}
