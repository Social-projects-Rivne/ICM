package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserve.rv_028.issuecitymonitor.IssueCityMonitorApplication;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.EventMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebAppConfiguration
public class EventControllerITest {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventMapper eventMapper;

    private Event event;

    @Before
    public void setup(){
        event = eventDao.findAll().get(1);
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
