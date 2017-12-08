package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;

import java.util.List;

public interface EventService {
//    EventDto add(EventDto eventDto);
    void deleteById(long id);
    List<EventDto> findAll();
    EventDto findById(long id);
    EventDto update(EventDto eventDto);
}
