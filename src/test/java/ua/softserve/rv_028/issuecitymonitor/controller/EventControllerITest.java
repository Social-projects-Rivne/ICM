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
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.EventMapper;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    private User user;
    private Event event;
    private List<Event> events;

    @Before
    public void setUp() {
        user = userDao.save(TestUtils.createUser(0));
        events = eventDao.save(TestUtils.createEventsList(user, LIST_SIZE));
        event = events.get(0);
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
    public void testUpdateEventSuccessfully(){
        String updatedTitle = "testUpdateTitle";
        String updatedDescription = "testUpdateDescription";
        EventDto eventDto = eventMapper.toDto(event);
        eventDto.setTitle(updatedTitle);
        eventDto.setDescription(updatedDescription);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<EventDto> httpEntity = new HttpEntity<>(eventDto,httpHeaders);
        ResponseEntity<EventDto> responseEntity = testRestTemplate.exchange("/api/events/"+eventDto.getId(),
                HttpMethod.PUT, httpEntity, EventDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        EventDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(updatedTitle, responseObject.getTitle());
        assertEquals(updatedDescription, responseObject.getDescription());
    }

    @Test
    public void testDeleteEventSuccessfully(){
        long prevCount = eventDao.count();
        testRestTemplate.delete("/api/events/"+event.getId());
        assertEquals(prevCount-1, eventDao.count());
    }

    @Test
    public void testEventNotFound(){
        ResponseEntity<EventDto> responseEntity = testRestTemplate.
                getForEntity("/api/events/-1", EventDto.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }



}
