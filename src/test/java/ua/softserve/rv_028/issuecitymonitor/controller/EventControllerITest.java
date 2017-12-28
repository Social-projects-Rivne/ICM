package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.EventMapper;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerITest {

    private static final int LIST_SIZE = 5;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventMapper eventMapper;

    private Event event;
    private List<Event> events;

    @Before
    public void setUp() {
        events = eventDao.save(TestUtils.createEventsList(LIST_SIZE));
        event = events.get(0);
    }

    //TODO soft delete using other method
    @After
    public void tearDown() {
        eventDao.delete(events);
    }

    @Test
    public void testGetEventsByPage() {
        ResponseEntity<PageImpl> responseEntity = testRestTemplate.getForEntity("/api/events", PageImpl.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        PageImpl responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(events.size(), responseObject.getContent().size());

        //TODO page constructor
    }

    @Test
    public void testGetEventSuccessfully(){
        ResponseEntity<EventDto> responseEntity = testRestTemplate.
                getForEntity("/api/events/"+event.getId(), EventDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
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

        System.out.println(httpEntity.toString());

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
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }



}
