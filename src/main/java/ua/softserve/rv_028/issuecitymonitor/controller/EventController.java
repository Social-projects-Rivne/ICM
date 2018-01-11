package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.service.EventService;

import static ua.softserve.rv_028.issuecitymonitor.Constants.PAGE_SIZE;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final Logger LOGGER = Logger.getLogger(EventController.class);

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public Page<EventDto> getAllByPage(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                       @RequestParam(name = "size", defaultValue = (""+PAGE_SIZE)) int pageSize,
                                       @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
                                       @RequestParam(value = "sort", defaultValue = "id") String sort) {
        LOGGER.debug("GET request for all events");
        return eventService.findAllByPage(pageNumber, pageSize, direction, sort);
    }

    @GetMapping(value = "/{id}")
    public EventDto getOne(@PathVariable long id) {
        LOGGER.debug("GET request");
        return eventService.findById(id);
    }

    @PutMapping("/{id}")
    public EventDto update(@RequestBody EventDto eventDto) {
        LOGGER.debug("PUT request");
        return eventService.update(eventDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        LOGGER.debug("DELETE request");
        eventService.deleteById(id);
    }
}
