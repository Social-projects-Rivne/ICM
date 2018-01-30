package ua.softserve.rv_028.issuecitymonitor.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentIssueDto {

    long id;
    IssueDto issueDto;
    UserDto userDto;
    String body;
    String initialDate;
}
