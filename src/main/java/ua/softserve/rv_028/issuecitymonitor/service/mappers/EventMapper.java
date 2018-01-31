package ua.softserve.rv_028.issuecitymonitor.service.mappers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.dto.EventLocationDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueLocationDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EventMapper extends MapperService<EventDto, Event> {

    UserMapper userMapper;

    @Override
    public EventDto toDto(Event entity) {
        EventDto dto = new EventDto();

        dto.setId(entity.getId());
        dto.setUserDto(userMapper.toDto(entity.getUser()));
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setInitialDate(entity.getInitialDate().format(DATE_FORMAT));
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setEndDate(entity.getEndDate().format(DATE_FORMAT));
        dto.setCategory(entity.getCategory());
        return dto;
    }

    public List<EventLocationDto> toLocationDtoList(List<Event> events) {
        return events.stream().map(this::toLocationDto).collect(Collectors.toList());
    }

    public EventLocationDto toLocationDto(Event entity) {
        EventLocationDto dto = new EventLocationDto();

        dto.setId(entity.getId());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        return dto;
    }

    @Override
    public Event toEntity(EventDto dto) {
        Event entity = new Event();

        entity.setUser(userMapper.toEntity(dto.getUserDto()));
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setInitialDate(LocalDateTime.parse(dto.getInitialDate(), DATE_FORMAT));
        entity.setEndDate(LocalDateTime.parse(dto.getEndDate(), DATE_FORMAT));
        entity.setCategory(dto.getCategory());
        return entity;
    }
}
