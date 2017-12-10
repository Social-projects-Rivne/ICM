package ua.softserve.rv_028.issuecitymonitor.dao;

import org.springframework.data.repository.CrudRepository;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;

import java.util.List;

public interface IssueDao extends CrudRepository<Issue, Long> {
    List<Issue> findAllByOrderByIdAsc();
}
