package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.exception.EventNotFoundException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LogManager.getLogger(EventServiceImpl.class.getName());

    private final EventDao eventDao;

    private final UserDao userDao;

    @Autowired
    public EventServiceImpl(EventDao eventDao, UserDao userDao){
        this.eventDao = eventDao;
        this.userDao = userDao;
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
        List<EventDto> eventDtos = new ArrayList<>();
        for(Event e : eventDao.findAllByOrderByIdAsc()){
            eventDtos.add(new EventDto(e));
        }
        LOGGER.debug("Found all events");
        return eventDtos;
    }

    @Override
    public EventDto findById(long id) throws EventNotFoundException{
        LOGGER.debug("Finding event by id " + id);
        Event event = findOne(id);
        LOGGER.debug("Found " + event.toString());
        return new EventDto(event);
    }

    @Override
    public EventDto update(EventDto eventDto) throws EventNotFoundException, ParseException {
        DATE_FORMAT.parse(eventDto.getInitialDate());
        DATE_FORMAT.parse(eventDto.getEndDate());

        Event event = findOne(eventDto.getId());
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setInitialDate(eventDto.getInitialDate());
        event.setEndDate(eventDto.getEndDate());
        event.setCategory(eventDto.getCategory());
        LOGGER.debug("Updating " + event.toString());
        eventDao.save(event);
        LOGGER.debug("Updated " + event.toString());
        return new EventDto(event);
    }

    private Event findOne(long id) throws EventNotFoundException{
        Event event = eventDao.findOne(id);
        if(event == null)
            throw new EventNotFoundException("Event not found");
        return event;
    }
}
