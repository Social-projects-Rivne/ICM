package ua.softserve.rv_028.issuecitymonitor.service.mappers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueLocationDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class IssueMapper extends MapperService<IssueDto, Issue> {

    UserMapper userMapper;

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

    public List<IssueLocationDto> toLocationDtoList(List<Issue> issues) {
        return issues.stream().map(this::toLocationDto).collect(Collectors.toList());
    }

    public IssueLocationDto toLocationDto(Issue entity) {
        IssueLocationDto dto = new IssueLocationDto();

        dto.setId(entity.getId());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        return dto;
    }
}
