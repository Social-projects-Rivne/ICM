package ua.softserve.rv_028.issuecitymonitor.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.CommentIssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.CommentIssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.CommentIssue;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.CommentIssueMapper;

import java.time.LocalDateTime;
import java.util.List;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CommentIssueService {

    UserDao userDao;

    IssueDao issueDao;

    CommentIssueDao commentIssueDao;

    CommentIssueMapper commentIssueMapper;

    public CommentIssueDto createComment(CommentIssueDto commentIssueDto, IssueDto issueDto){
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findUserByUsername(userAuth.getName());
        Issue issue = issueDao.findOne(issueDto.getId());
        CommentIssue commentIssue = commentIssueDao.save(new CommentIssue(issue, user, commentIssueDto.getBody(),
                LocalDateTime.parse(commentIssueDto.getInitialDate(), DATE_FORMAT)));
        log.debug("Create comment for issue");
        return commentIssueMapper.toDto(commentIssue);
    }

    public List<CommentIssueDto> findAllCommentById(Long id){
        List<CommentIssue> listCommentIssue = commentIssueDao.findAllByIssueEquals(id);
        log.debug("All comments for issue");
        return commentIssueMapper.toDtoList(listCommentIssue);
    }
}
