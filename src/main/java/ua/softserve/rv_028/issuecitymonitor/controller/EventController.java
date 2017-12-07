package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.exception.EventNotFoundException;
import ua.softserve.rv_028.issuecitymonitor.service.EventService;

import java.text.ParseException;

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
    public ResponseEntity<?> getAll(){
        LOGGER.debug("GET request for all users");
        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOne(@PathVariable long id){
        LOGGER.debug("GET request for event with id "+id);
        try {
            EventDto result = eventService.findById(id);
            LOGGER.debug("GET request successful for event with id "+id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (EventNotFoundException e) {
            LOGGER.error("GET request failed for event with id "+id);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody EventDto eventDto){
        LOGGER.debug("PUT request for event with id "+id);
        try {
            EventDto result = eventService.update(eventDto);
            LOGGER.debug("PUT request successful for event with id "+id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (EventNotFoundException e) {
            LOGGER.error("PUT request failed for event with id "+id);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            LOGGER.error("PUT request failed for event with id "+id+" due to incorrect date format");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        LOGGER.debug("DELETE request for event with id "+id);
        try {
            eventService.deleteById(id);
            LOGGER.debug("DELETE request successful for event with id "+id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EventNotFoundException e) {
            LOGGER.error("DELETE request failed for event with id "+id);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
