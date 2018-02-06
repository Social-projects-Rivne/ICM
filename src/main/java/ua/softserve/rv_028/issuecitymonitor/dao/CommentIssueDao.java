package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.CommentIssue;

import java.util.List;

public interface CommentIssueDao extends JpaRepository<CommentIssue, Long> {
    List<CommentIssue> findAllByIssueEquals(Long id);
}
