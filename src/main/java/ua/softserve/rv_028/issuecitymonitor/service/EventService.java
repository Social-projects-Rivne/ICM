package ua.softserve.rv_028.issuecitymonitor.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
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
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EventService {

    EventDao eventDao;

    EventMapper eventMapper;

    public Page<EventDto> findAllByPage(int pageNumber, int pageSize, Sort.Direction direction, String columns) {
        String[] columnArray = columns.split(",");
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, direction, columnArray);
        Page<EventDto> eventDtos = eventMapper.toDtoPage(eventDao.findAll(pageRequest));
        log.debug("Found all events");
        return eventDtos;
    }

    public EventDto findById(long id) {
        Event event = findOne(id);
        log.debug("Found " + event.toString());
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
        log.debug("Updated " + event.toString());
        return eventMapper.toDto(event);
    }

    public void deleteById(long id) {
        eventDao.delete(id);
        log.debug("Deleted event " + id);
    }

    private Event findOne(long id){
        Event event = eventDao.findOne(id);
        if(event == null){
            throw new IllegalArgumentException("event id not found:" + id);
        }
        return event;
    }
}
