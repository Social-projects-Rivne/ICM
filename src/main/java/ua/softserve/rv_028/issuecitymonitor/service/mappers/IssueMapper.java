package ua.softserve.rv_028.issuecitymonitor.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;

import java.time.LocalDateTime;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class IssueMapper extends MapperService<IssueDto, Issue> {

    private final UserMapper userMapper;

    @Autowired
    public IssueMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public IssueDto toDto(Issue entity) {
        IssueDto dto = new IssueDto();

        dto.setId(entity.getId());
        dto.setUserDto(userMapper.toDto(entity.getUser()));
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setInitialDate(entity.getInitialDate().format(DATE_FORMAT));
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setCategory(entity.getCategory());
        return dto;
    }

    @Override
    public Issue toEntity(IssueDto dto) {
        Issue entity = new Issue();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setUser(userMapper.toEntity(dto.getUserDto()));
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setInitialDate(LocalDateTime.parse(dto.getInitialDate(), DATE_FORMAT));
        entity.setCategory(dto.getCategory());
        return entity;
    }
}
