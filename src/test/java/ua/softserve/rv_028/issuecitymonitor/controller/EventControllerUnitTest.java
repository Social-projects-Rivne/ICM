package ua.softserve.rv_028.issuecitymonitor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.exception.EventNotFoundException;
import ua.softserve.rv_028.issuecitymonitor.service.EventService;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerUnitTest {

    private final static String TEST_TITLE = "test";
    private final static String TEST_DESCRIPTION = "testDescription";

    private MockMvc mockMvc;

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void testGetEventSuccessfully() throws Exception{
        EventDto eventDto = new EventDto();

        String expectedValue = new ObjectMapper().writeValueAsString(eventDto);

        when(eventService.findById(anyInt())).thenReturn(eventDto);

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedValue));
    }

    @Test
    public void testGetEventNotFound() throws Exception {
        when(eventService.findById(anyInt())).thenThrow(new EventNotFoundException("event not found"));

        mockMvc.perform(get("api/events/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateEventSuccessfully() throws Exception{
        EventDto eventDto = new EventDto();
        eventDto.setTitle(TEST_TITLE);
        eventDto.setDescription(TEST_DESCRIPTION);

        String jsonObject = new ObjectMapper().writeValueAsString(eventDto);
        assertThat(jsonObject, containsString(TEST_TITLE));
        assertThat(jsonObject, containsString(TEST_DESCRIPTION));

        when(eventService.update(any(EventDto.class))).thenReturn(eventDto);

        mockMvc.perform(put("/api/events/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(jsonObject));
    }

    @Test
    public void testUpdateEventNotFound() throws Exception{
        String jsonObject = new ObjectMapper().writeValueAsString(new EventDto());

        when(eventService.update(any(EventDto.class))).thenThrow(new EventNotFoundException("event not found"));

        mockMvc.perform(put("/api/events/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteEventSuccessfully() throws Exception{
        doNothing().when(eventService).deleteById(1); //This is obvious

        mockMvc.perform(delete("/api/events/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEventNotFound() throws Exception {
        doThrow(new EventNotFoundException("event not found")).when(eventService).deleteById(1);

        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isNotFound());
    }

}
