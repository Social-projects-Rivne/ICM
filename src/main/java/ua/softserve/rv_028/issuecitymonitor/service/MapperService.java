package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;

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

    public User fromDtoToEntity(UserDto userDto){
        User user = new User();

        user.setId(userDto.getId());
        user.setUserRole(userDto.getUserRole());
        user.setRegistrationDate(userDto.getRegistrationDate());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setUserAgreement(userDto.isUserAgreement());
        user.setUserStatus(userDto.getUserStatus());
        user.setDeleteDate(userDto.getDeleteDate());
        user.setAvatarUrl(userDto.getAvatarUrl());
        return user;
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

    public Event fromEntityToDto(EventDto eventDto){
        Event event = new Event();

        event.setId(eventDto.getId());
        event.setUser(fromDtoToEntity(eventDto.getUserDto()));
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setInitialDate(eventDto.getInitialDate());
        event.setLatitude(eventDto.getLatitude());
        event.setLongitude(eventDto.getLongitude());
        event.setEndDate(eventDto.getEndDate());
        event.setCategory(eventDto.getCategory());
        return event;
    }


    public PetitionDto fromEntityToDto(Petition petitionEntity){
        PetitionDto petition = new PetitionDto();

        petition.setId(petitionEntity.getId());
        petition.setUserDto(fromEntityToDto(petitionEntity.getUser()));
        petition.setTitle(petitionEntity.getTitle());
        petition.setDescription(petitionEntity.getDescription());
        petition.setInitialDate(petitionEntity.getInitialDate());
        petition.setCategory(petitionEntity.getCategory());
        return petition;
    }

    public Petition fromEntityToDto(PetitionDto petitionDto){
        Petition petition = new Petition();

        petition.setId(petitionDto.getId());
        petition.setUser(fromDtoToEntity(petitionDto.getUserDto()));
        petition.setTitle(petitionDto.getTitle());
        petition.setDescription(petitionDto.getDescription());
        petition.setInitialDate(petitionDto.getInitialDate());
        petition.setCategory(petitionDto.getCategory());
        return petition;
    }

}
