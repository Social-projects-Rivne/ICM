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

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class EventService {

    private static final Logger LOGGER = Logger.getLogger(EventService.class);

    private final EventDao eventDao;

    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventDao eventDao, EventMapper eventMapper){
        this.eventDao = eventDao;
        this.eventMapper = eventMapper;
    }

    public Page<EventDto> findAllByPage(int pageNumber, int pageSize, Sort.Direction direction, String columns) {
        String[] columnArray = columns.split(",");
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, direction, columnArray);
        Page<EventDto> eventDtos = eventMapper.toDtoPage(eventDao.findAll(pageRequest));
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
        if(eventDto.getInitialDate() != null) {
            event.setInitialDate(LocalDateTime.parse(eventDto.getInitialDate(), DATE_FORMAT));
        }
        if(eventDto.getEndDate() != null) {
            event.setEndDate(LocalDateTime.parse(eventDto.getEndDate(), DATE_FORMAT));
        }
        event.setCategory(eventDto.getCategory());

        event = eventDao.save(event);
        LOGGER.debug("Updated " + event.toString());
        return eventMapper.toDto(event);
    }

    public void deleteById(long id) {
        eventDao.delete(id);
        LOGGER.debug("Deleted event " + id);
    }

    private Event findOne(long id){
        Event event = eventDao.findOne(id);
        if(event == null){
            throw new IllegalArgumentException("event id not found:" + id);
        }
        return event;
    }
}
