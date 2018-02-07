package ua.softserve.rv_028.issuecitymonitor.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.EventMapper;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createEventsList;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerITest {

    private static final int LIST_SIZE = 5;

    private static final int PAGE_SIZE = 2;
    private static final int PAGE_OFFSET = 1;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventController controller;

    private User user;
    private Event event;
    private List<Event> events;

    private static User USER = TestUtils.createAdmin(0);
    private static final String USERNAME = "mock-test@mail.com";

    @Before
    public void setUp() {
        user = userDao.save(createUser(0));
        events = eventDao.save(createEventsList(user, LIST_SIZE));
        event = events.get(0);

        USER.setUsername(USERNAME);
        User user = userDao.findUserByUsername(USERNAME);
        if (user == null)
            userDao.save(USER);
        else
            USER = user;
        USER.setUserRole(UserRole.ADMIN);
        userDao.save(USER);
    }

    @After
    public void tearDown() {
        eventDao.delete(events);
        userDao.delete(user);
    }

    @Test
    public void testGetEventsByPage() {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/api/events?size="+PAGE_SIZE+"&page="+PAGE_OFFSET, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode content = objectMapper.readTree(responseEntity.getBody()).path("content");
            assertEquals(PAGE_SIZE, content.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetEventSuccessfully(){
        ResponseEntity<EventDto> responseEntity = testRestTemplate.
                getForEntity("/api/events/"+event.getId(), EventDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        EventDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(event.getTitle(), responseObject.getTitle());
        assertEquals(event.getDescription(), responseObject.getDescription());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void testUpdateEventSuccessfully(){
        String updatedTitle = "testUpdateTitle";
        String updatedDescription = "testUpdateDescription";
        EventDto eventDto = eventMapper.toDto(event);
        eventDto.setTitle(updatedTitle);
        eventDto.setDescription(updatedDescription);

        EventDto updatedEvent = controller.update(eventDto);

        assertEquals(updatedTitle, updatedEvent.getTitle());
        assertEquals(updatedDescription, updatedEvent.getDescription());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void testDeleteEventSuccessfully(){
        long prevCount = eventDao.count();
        controller.delete(event.getId());
        assertEquals(prevCount-1, eventDao.count());
    }

    @Test
    public void testEventNotFound(){
        ResponseEntity<EventDto> responseEntity = testRestTemplate.
                getForEntity("/api/events/-1", EventDto.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }



}
