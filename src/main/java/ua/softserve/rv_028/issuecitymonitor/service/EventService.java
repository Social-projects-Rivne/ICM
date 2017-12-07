package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.exception.EventNotFoundException;

import java.util.List;

public interface EventService {
//    EventDto add(EventDto eventDto);
    void deleteById(long id) throws EventNotFoundException;
    List<EventDto> findAll();
    EventDto findById(long id) throws EventNotFoundException;
    EventDto update(EventDto eventDto) throws EventNotFoundException;
}
