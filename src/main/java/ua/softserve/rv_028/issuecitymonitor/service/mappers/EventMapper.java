package ua.softserve.rv_028.issuecitymonitor.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.time.LocalDateTime;
import java.util.IllegalFormatException;
import java.util.Optional;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class EventMapper extends MapperService<EventDto, Event> {

    private final UserMapper userMapper;

    @Autowired
    public EventMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public EventDto toDto(Event entity) {
        EventDto dto = new EventDto();

        dto.setId(entity.getId());
        if(entity.getUser() != null) {
            dto.setUserDto(userMapper.toDto(entity.getUser()));
        }
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());

        if(entity.getInitialDate() != null) {
            dto.setInitialDate(entity.getInitialDate().format(DATE_FORMAT));
        }
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        if(entity.getEndDate() != null) {
            dto.setEndDate(entity.getEndDate().format(DATE_FORMAT));
        }
        dto.setCategory(entity.getCategory());
        return dto;
    }

    @Override
    public Event toEntity(EventDto dto) {
        Event entity = new Event();

        if(dto.getUserDto() != null) {
            entity.setUser(userMapper.toEntity(dto.getUserDto()));
        }
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        if(dto.getInitialDate() != null) {
            entity.setInitialDate(LocalDateTime.parse(dto.getInitialDate(), DATE_FORMAT));
        }
        if(dto.getEndDate() != null) {
            entity.setEndDate(LocalDateTime.parse(dto.getEndDate(), DATE_FORMAT));
        }
        entity.setCategory(dto.getCategory());
        return entity;
    }
}
