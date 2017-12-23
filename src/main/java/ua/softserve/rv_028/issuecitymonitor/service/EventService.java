package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.EventMapper;

import java.time.LocalDateTime;
import java.util.List;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class EventService {

    private static final Logger LOGGER = Logger.getLogger(EventService.class.getName());

    private final EventDao eventDao;

    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventDao eventDao, EventMapper eventMapper){
        this.eventDao = eventDao;
        this.eventMapper = eventMapper;
    }

    public void deleteById(long id) {
        Event event = findOne(id);
        eventDao.delete(event);
        LOGGER.debug("Deleted " + event.toString());
    }

    public Page<EventDto> findAllByPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        Page<EventDto> eventDtos = eventDao.findAll(pageRequest).map(eventMapper::toDto);
        LOGGER.debug("Found all events");
        return eventDtos;
    }

    public EventDto findById(long id) {
        Event event = findOne(id);
        LOGGER.debug("Found " + event.toString());
        return eventMapper.toDto(event);
    }

    public EventDto update(EventDto eventDto) {
        Event event = findOne(eventDto.getId());
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setInitialDate(LocalDateTime.parse(eventDto.getInitialDate(), DATE_FORMAT));
        event.setEndDate(LocalDateTime.parse(eventDto.getEndDate(), DATE_FORMAT));
        event.setCategory(eventDto.getCategory());

        eventDao.save(event);
        LOGGER.debug("Updated " + event.toString());
        return eventMapper.toDto(event);
    }

    private Event findOne(long id){
        Event event = eventDao.findOne(id);
        if(event == null){
            throw new IllegalStateException("event id not found:" + id);
        }
        return event;
    }
}
