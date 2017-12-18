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

    public <D, E> List<D> toDtoList(List<E> entities) {
        return entities.stream().map(this::<D, E>toDto).collect(Collectors.toList());
    }

    public <E, D> List<E> toEntityList(List<D> entities) {
        return entities.stream().map(this::<D, E>toEntity).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <D, E> D toDto(E entity){
        D dto = null;
        if(entity instanceof Event) {
            dto = (D) toDto(((Event) entity));
        } else if(entity instanceof User) {
            dto = (D) toDto(((User) entity));
        } else if(entity instanceof Issue) {
            dto = (D) toDto(((Issue) entity));
        }
        if(dto == null) {
            throw new UnsupportedOperationException("wrong type");
        }
        return dto;
    }

    @SuppressWarnings("unchecked")
    public <D, E> E toEntity(D dto) {
        E entity = null;
        if(dto instanceof EventDto) {
            entity = (E) toEntity(((EventDto) dto));
        } else if(dto instanceof UserDto) {
            entity = (E) toEntity(((UserDto) dto));
        } else if(dto instanceof IssueDto) {
            entity = (E) toEntity(((IssueDto) dto));
        }
        if(dto == null) {
            throw new UnsupportedOperationException("wrong type");
        }
        return entity;
    }

    private UserDto toDto(User entity){
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setUserRole(entity.getUserRole());
        //dto.setRegistrationDate(entity.getRegistrationDate().format(DATE_FORMAT));
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getUsername());
        dto.setPhone(entity.getPhone());
        dto.setUserAgreement(entity.isUserAgreement());
        dto.setUserStatus(entity.getUserStatus());
        //dto.setDeleteDate(entity.getDeleteDate().format(DATE_FORMAT));
        dto.setAvatarUrl(entity.getAvatarUrl());
        return dto;
    }

    private IssueDto toDto(Issue entity){
        IssueDto dto = new IssueDto();

        dto.setId(entity.getId());
        dto.setUserDto(toDto(entity.getUser()));
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setInitialDate(entity.getInitialDate().format(DATE_FORMAT));
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setCategory(entity.getCategory());
        return dto;
    }

    private EventDto toDto(Event entity){
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

    private User toEntity(UserDto dto){
        User entity = new User();

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

    private Event toEntity(EventDto dto){
        Event entity = new Event();

        entity.setUser(toEntity(dto.getUserDto()));
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setInitialDate(LocalDateTime.parse(dto.getInitialDate(), DATE_FORMAT));
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setEndDate(LocalDateTime.parse(dto.getEndDate(), DATE_FORMAT));
        entity.setCategory(dto.getCategory());
        return entity;
    }

    private Issue toEntity(IssueDto dto){
        Issue entity = new Issue();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setUser(toEntity(dto.getUserDto()));
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setInitialDate(LocalDateTime.parse(dto.getInitialDate(), DATE_FORMAT));
        entity.setCategory(dto.getCategory());
        return entity;
    }
}
