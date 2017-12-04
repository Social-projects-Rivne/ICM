package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.exception.EventNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LogManager.getLogger(EventServiceImpl.class.getName());

    private final EventDao eventDao;

    @Autowired
    public EventServiceImpl(EventDao eventDao){
        this.eventDao = eventDao;
    }

    @Override
    public EventDto add(EventDto eventDto) {
        Event event = new Event();
        event.setUser(eventDto.getUser());
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setLongitude(eventDto.getLongitude());
        event.setLatitude(eventDto.getLatitude());
        event.setInitialDate(eventDto.getInitialDate());
        event.setEndDate(eventDto.getEndDate());
        event.setCategory(eventDto.getCategory());
        LOGGER.debug("Adding " + event.toString());
        event = eventDao.save(event);
        LOGGER.debug("Added " + event.toString());
        return new EventDto(event);
    }

    @Override
    public void deleteById(long id) throws EventNotFoundException{
        Event event = findOne(id);
        LOGGER.debug("Deleting " + event.toString());
        eventDao.delete(event);
        LOGGER.debug("Deleted " + event.toString());
    }

    @Override
    public List<EventDto> findAll() {
        LOGGER.debug("Finding all events");
        List<EventDto> eventDtos = eventDao.findAll().stream().map(EventDto::new).collect(Collectors.toList());
        LOGGER.debug("Found all events" + eventDtos.toString());
        return eventDtos;
    }

    @Override
    public EventDto findById(long id) throws EventNotFoundException{
        LOGGER.debug("Finding event by " + id + " id");
        Event event = findOne(id);
        LOGGER.debug("Found " + event.toString());
        return new EventDto(event);
    }

    @Override
    public EventDto update(EventDto eventDto) throws EventNotFoundException{
        Event event = findOne(eventDto.getId());
        event.setUser(eventDto.getUser());
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setInitialDate(eventDto.getInitialDate());
        event.setLatitude(eventDto.getLatitude());
        event.setLongitude(eventDto.getLongitude());
        event.setEndDate(eventDto.getEndDate());
        event.setCategory(eventDto.getCategory());
        LOGGER.debug("Updating " + event.toString());
        event = eventDao.save(event);
        LOGGER.debug("Updated " + event.toString());
        return eventDto;
    }

    private Event findOne(Long id) throws EventNotFoundException{
        Event event = eventDao.findOne(id);
        if(event == null)
            throw new EventNotFoundException("Event not found");
        return event;
    }
}
