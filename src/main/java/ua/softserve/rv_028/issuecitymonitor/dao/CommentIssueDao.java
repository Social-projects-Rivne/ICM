package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.CommentIssue;

public interface CommentDao extends JpaRepository<CommentIssue, Long> {
}
