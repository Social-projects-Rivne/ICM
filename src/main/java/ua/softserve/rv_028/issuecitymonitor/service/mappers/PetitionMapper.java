package ua.softserve.rv_028.issuecitymonitor.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;

import java.time.LocalDateTime;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class PetitionMapper extends MapperService<PetitionDto, Petition>{

    private final UserMapper userMapper;

    @Autowired
    public PetitionMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public PetitionDto toDto(Petition entity) {
        PetitionDto dto = new PetitionDto();

        dto.setId(entity.getId());
        dto.setUserDto(userMapper.toDto(entity.getUser()));
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setInitialDate(entity.getInitialDate().format(DATE_FORMAT));
        dto.setCategory(entity.getCategory());
        return dto;
    }

    @Override
    public Petition toEntity(PetitionDto dto) {
        Petition entity = new Petition();

        entity.setUser(userMapper.toEntity(dto.getUserDto()));
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setInitialDate(LocalDateTime.parse(dto.getInitialDate(), DATE_FORMAT));
        entity.setCategory(dto.getCategory());
        return entity;
    }
}
