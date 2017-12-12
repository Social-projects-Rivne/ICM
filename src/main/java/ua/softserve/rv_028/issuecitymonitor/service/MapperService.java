package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

@Service
public class MapperService {

    public UserDto fromEntityToDto(User userEntity){
        UserDto dto = new UserDto();
        dto.setId(userEntity.getId());
        dto.setUserRole(userEntity.getUserRole());
        dto.setRegistrationDate(userEntity.getRegistrationDate());
        dto.setFirstName(userEntity.getFirstName());
        dto.setLastName(userEntity.getLastName());
        dto.setPassword(userEntity.getPassword());
        dto.setEmail(userEntity.getUsername());
        dto.setPhone(userEntity.getPhone());
        dto.setUserAgreement(userEntity.isUserAgreement());
        dto.setUserStatus(userEntity.getUserStatus());
        dto.setDeleteDate(userEntity.getDeleteDate());
        dto.setAvatarUrl(userEntity.getAvatarUrl());
        return dto;
    }

    public EventDto fromEntityToDto(Event eventEntity){
        EventDto event = new EventDto();

        event.setId(eventEntity.getId());
        event.setUserDto(fromEntityToDto(eventEntity.getUser()));
        event.setTitle(eventEntity.getTitle());
        event.setDescription(eventEntity.getDescription());
        event.setInitialDate(eventEntity.getInitialDate());
        event.setLatitude(eventEntity.getLatitude());
        event.setLongitude(eventEntity.getLongitude());
        event.setEndDate(eventEntity.getEndDate());
        event.setCategory(eventEntity.getCategory());
        return event;
    }
}
