package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.service.EventService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

    private final static String TEST_TITLE = "test";
    private final static String TEST_DESCRIPTION = "testDescription";
    private final static IllegalArgumentException EXCEPTION_NOT_FOUND = new IllegalArgumentException("event not found");

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Test
    public void testGetEventSuccessfully(){
        EventDto event = new EventDto();
        event.setTitle(TEST_TITLE);
        event.setDescription(TEST_DESCRIPTION);

        when(eventService.findById(1)).thenReturn(event);

        EventDto dto = eventController.getOne(1);

        assertEquals(TEST_TITLE,dto.getTitle());
        assertEquals(TEST_DESCRIPTION,dto.getDescription());
    }

    @Test
    public void testGetEventNotFound() {
        when(eventService.findById(-1)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            eventController.getOne(-1);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testUpdateEventSuccessfully(){
        EventDto eventDto = new EventDto();
        eventDto.setId(1);
        eventDto.setTitle(TEST_TITLE);
        eventDto.setDescription(TEST_DESCRIPTION);
        when(eventService.update(eventDto)).thenReturn(eventDto);

        EventDto success = eventController.update(eventDto);

        assertEquals(TEST_TITLE,success.getTitle());
        assertEquals(TEST_DESCRIPTION,success.getDescription());
    }

    @Test
    public void testUpdateEventNotFound(){
        EventDto eventDto = new EventDto();
        eventDto.setId(1);
        when(eventService.update(eventDto)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            eventController.update(eventDto);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testDeleteEventNotFound(){
        doThrow(EXCEPTION_NOT_FOUND).when(eventService).deleteById(1);

        try {
            eventController.delete(1);
            fail("expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }
}
