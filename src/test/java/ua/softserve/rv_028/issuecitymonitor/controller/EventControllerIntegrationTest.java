package ua.softserve.rv_028.issuecitymonitor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableAutoConfiguration(exclude = {DBSeeder.class})
public class EventControllerIntegrationTest {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private Event event;
    private User user;

    @Before
    public void setup(){
        User u = new User();
        u.setFirstName("testFirstName");
        u.setFirstName("testLastName");
        user = userDao.save(u);

        Event e = new Event();
        e.setTitle("testTitle");
        e.setUser(user);
        e.setDescription("testDescription");
        event = eventDao.save(e);
    }

    @Test
    public void testGetEventSuccessfully() throws Exception{
        ResponseEntity<EventDto> responseEntity = testRestTemplate.
                getForEntity("/api/events/"+event.getId(), EventDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        EventDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals("testTitle", responseObject.getTitle());
        assertEquals("testDescription", responseObject.getDescription());
    }

    @Test
    public void testGetEventNotFound() throws Exception{
        ResponseEntity<EventDto> responseEntity = testRestTemplate.
                getForEntity("/api/events/-1", EventDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}
