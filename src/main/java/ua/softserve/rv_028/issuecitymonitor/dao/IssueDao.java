package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;

import java.util.List;

public interface IssueDao extends JpaRepository<Issue, Long>{
    List<Issue> findAllByOrderByIdAsc();
}
