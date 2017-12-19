package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.EventMapper;

import java.time.LocalDateTime;
import java.util.List;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = Logger.getLogger(EventServiceImpl.class.getName());

    private final EventDao eventDao;

    private final EventMapper mapperService;

    @Autowired
    public EventServiceImpl(EventDao eventDao, EventMapper mapperService){
        this.eventDao = eventDao;
        this.mapperService = mapperService;
    }

    @Override
    public void deleteById(long id) {
        Event event = findOne(id);
        LOGGER.debug("Deleting " + event.toString());
        eventDao.delete(event);
        LOGGER.debug("Deleted " + event.toString());
    }

    @Override
    public List<EventDto> findAll() {
        LOGGER.debug("Finding all events");
        List<EventDto> eventDtos = mapperService.toDtoList(eventDao.findAllByOrderByIdAsc());
        LOGGER.debug("Found all events");
        return eventDtos;
    }

    @Override
    public EventDto findById(long id) {
        LOGGER.debug("Finding event by id " + id);
        Event event = findOne(id);
        LOGGER.debug("Found " + event.toString());
        return mapperService.toDto(event);
    }

    @Override
    public EventDto update(EventDto eventDto) {
        Event event = findOne(eventDto.getId());
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setInitialDate(LocalDateTime.parse(eventDto.getInitialDate(), DATE_FORMAT));
        event.setEndDate(LocalDateTime.parse(eventDto.getEndDate(), DATE_FORMAT));
        event.setCategory(eventDto.getCategory());
        LOGGER.debug("Updating " + event.toString());
        eventDao.save(event);
        LOGGER.debug("Updated " + event.toString());
        return mapperService.toDto(event);
    }

    private Event findOne(long id){
        Event event = eventDao.findOne(id);
        if(event == null){
            throw new IllegalStateException("event id not found:" + id);
        }
        return event;
    }
}
