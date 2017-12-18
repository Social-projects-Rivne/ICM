package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;


@Service
public class MapperService {


    public UserDto toDto(User entity){
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setUserRole(entity.getUserRole());
        dto.setRegistrationDate(entity.getRegistrationDate().format(DATE_FORMAT));
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getUsername());
        dto.setPhone(entity.getPhone());
        dto.setUserAgreement(entity.isUserAgreement());
        dto.setUserStatus(entity.getUserStatus());
        dto.setDeleteDate(entity.getDeleteDate().format(DATE_FORMAT));
        dto.setAvatarUrl(entity.getAvatarUrl());
        return dto;
    }

    public IssueDto toDto(Issue entity){
        IssueDto dto = new IssueDto();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setInitialDate(entity.getInitialDate().format(DATE_FORMAT));
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setUserDto(toDto(entity.getUser()));
        dto.setCategory(entity.getCategory());
        return dto;
    }

    public EventDto toDto(Event entity){
        EventDto dto = new EventDto();

        dto.setId(entity.getId());
        dto.setUserDto(toDto(entity.getUser()));
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setInitialDate(entity.getInitialDate().format(DATE_FORMAT));
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setEndDate(entity.getEndDate().format(DATE_FORMAT));
        dto.setCategory(entity.getCategory());
        return dto;
    }

    public User toEntity(UserDto dto){
        User entity = new User();

        entity.setId(dto.getId());
        entity.setUserRole(dto.getUserRole());
        entity.setRegistrationDate(LocalDateTime.parse(dto.getRegistrationDate(), DATE_FORMAT));
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setUserAgreement(dto.isUserAgreement());
        entity.setUserStatus(dto.getUserStatus());
        entity.setDeleteDate(LocalDateTime.parse(dto.getDeleteDate(), DATE_FORMAT));
        entity.setAvatarUrl(dto.getAvatarUrl());
        return entity;
    }

    public Event toEntity(EventDto dto){
        Event entity = new Event();

        entity.setId(dto.getId());
        entity.setUser(toEntity(dto.getUserDto()));
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setInitialDate(LocalDateTime.parse(dto.getInitialDate(), DATE_FORMAT));
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setEndDate(LocalDateTime.parse(dto.getEndDate()));
        entity.setCategory(dto.getCategory());
        return entity;
    }

    //TODO GENERIFICATION!!!
    public List<EventDto> toDtoList(List<Event> entities) {
        return entities.stream().map(e -> {
            return toDto(e);
        }).collect(Collectors.toList());
    }
}
