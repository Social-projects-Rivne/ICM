package ua.softserve.rv_028.issuecitymonitor.service.mappers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dto.CommentIssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.CommentIssue;

import java.time.LocalDateTime;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentIssueMapper extends MapperService<CommentIssueDto, CommentIssue>{

    IssueMapper issueMapper;
    UserMapper userMapper;

    @Override
    public CommentIssueDto toDto(CommentIssue entity) {
        CommentIssueDto dto = new CommentIssueDto();

        dto.setId(entity.getId());
        dto.setIssueDto(issueMapper.toDto(entity.getIssue()));
        dto.setUserDto(userMapper.toDto(entity.getUser()));
        dto.setBody(entity.getBody());
        dto.setInitialDate(entity.getInitialDate().format(DATE_FORMAT));
        return dto;
    }

    @Override
    public CommentIssue toEntity(CommentIssueDto dto) {
        CommentIssue entity = new CommentIssue();

        entity.setIssue(issueMapper.toEntity(dto.getIssueDto()));
        entity.setUser(userMapper.toEntity(dto.getUserDto()));
        entity.setBody(dto.getBody());
        entity.setInitialDate(LocalDateTime.parse(dto.getInitialDate(), DATE_FORMAT));
        return entity;
    }
}
