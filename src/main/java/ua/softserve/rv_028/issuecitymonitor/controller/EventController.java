package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final Logger LOGGER = Logger.getLogger(EventController.class.getName());

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventDto> getAll(){
        LOGGER.debug("GET request for all users");
        return eventService.findAll();
    }

    @GetMapping(value = "/{id}")
    public EventDto getOne(@PathVariable long id){
        LOGGER.debug("GET request");
        return eventService.findById(id);
    }

    @PutMapping("/{id}")
    public EventDto update(@RequestBody EventDto eventDto){
        LOGGER.debug("PUT request");
        return eventService.update(eventDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        LOGGER.debug("DELETE request");
        eventService.deleteById(id);
    }
}
