package ua.softserve.rv_028.issuecitymonitor.service.mappers;

import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.time.LocalDateTime;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class UserMapper extends MapperService<UserDto, User> {

    @Override
    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setUserRole(entity.getUserRole());
        if(entity.getRegistrationDate() != null) {
            dto.setRegistrationDate(entity.getRegistrationDate().format(DATE_FORMAT));
        }
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getUsername());
        dto.setPhone(entity.getPhone());
        dto.setUserAgreement(entity.isUserAgreement());
        dto.setUserStatus(entity.getUserStatus());

        if(entity.getDeleteDate() != null) {
            dto.setDeleteDate(entity.getDeleteDate().format(DATE_FORMAT));
        }
        dto.setAvatarUrl(entity.getAvatarUrl());
        return dto;
    }

    @Override
    public User toEntity(UserDto dto) {
        User entity = new User();

        entity.setUserRole(dto.getUserRole());
        if(dto.getRegistrationDate() != null) {
            entity.setRegistrationDate(LocalDateTime.parse(dto.getRegistrationDate(), DATE_FORMAT));
        }
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setUserAgreement(dto.isUserAgreement());
        entity.setUserStatus(dto.getUserStatus());
        if(dto.getDeleteDate() != null) {
            entity.setDeleteDate(LocalDateTime.parse(dto.getDeleteDate(), DATE_FORMAT));
        }
        entity.setAvatarUrl(dto.getAvatarUrl());
        return entity;
    }
}
