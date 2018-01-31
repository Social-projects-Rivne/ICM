package ua.softserve.rv_028.issuecitymonitor.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.dto.EventLocationDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueLocationDto;
import ua.softserve.rv_028.issuecitymonitor.service.EventService;

import java.util.List;

import static ua.softserve.rv_028.issuecitymonitor.Constants.PAGE_SIZE;

@RestController
@RequestMapping("/api/events")
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EventController {

    EventService eventService;

    @GetMapping
    public Page<EventDto> getAllByPage(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                       @RequestParam(name = "size", defaultValue = (""+PAGE_SIZE)) int pageSize,
                                       @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
                                       @RequestParam(value = "sort", defaultValue = "id") String sort) {
        log.debug("GET request for all events");
        return eventService.findAllByPage(pageNumber, pageSize, direction, sort);
    }

    @GetMapping(value = "/{id}")
    public EventDto getOne(@PathVariable long id) {
        log.debug("GET request");
        return eventService.findById(id);
    }

    @PutMapping("/{id}")
    public EventDto update(@RequestBody EventDto eventDto) {
        log.debug("PUT request");
        return eventService.update(eventDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.debug("DELETE request");
        eventService.deleteById(id);
    }


    @GetMapping("/map")
    public List<EventLocationDto> getAll(){
        log.debug("GET request");
        return eventService.findAll();
    }
}
