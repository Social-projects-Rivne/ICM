package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Service
public class IssueService {

    private static final Logger LOGGER = Logger.getLogger(IssueService.class.getName());

    private IssueDao issueDao;

    @Autowired
    public IssueService(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    public List<IssueDto> findAll() {

        LOGGER.debug("Finding all issues");
        List<IssueDto> issueDto = new ArrayList<>();
        for (Issue issue : issueDao.findAllByOrderByIdAsc()) {
            issueDto.add(new IssueDto(issue));
        }
        LOGGER.debug("Finded all issues");

        return issueDto;
    }

    public Issue findOne(long id) {

        Issue issue = issueDao.findOne(id);
        checkState(issue != null, "Issue not found");
        LOGGER.debug("Find one " + issue.toString());
        return issue;
    }

    public IssueDto findById(long id) {

        Issue issue = findOne(id);
        LOGGER.debug("Finded by id " + issue.toString());

        return new IssueDto(issue);
    }

    public IssueDto addIssue(IssueDto issueDto) {

        LOGGER.debug("Adding issue");
        Issue issue = new Issue();
        issue.setUser(new User(issueDto.getUserDto()));
        issue.setTitle(issueDto.getTitle());
        issue.setDescription(issueDto.getDescription());
        issue.setLongitude(issueDto.getLongitude());
        issue.setLatitude(issueDto.getLatitude());
        issue.setInitialDate(issueDto.getInitialDate());
        issue.setCategory(issueDto.getCategory());
        issueDao.save(issue);
        LOGGER.debug("Added " + issue.toString());

        return new IssueDto(issue);
    }

    public IssueDto editIssue(IssueDto issueDto) {

        LOGGER.debug("Edit issue");
        Issue issue = findOne(issueDto.getId());
        issue.setTitle(issueDto.getTitle());
        issue.setDescription(issueDto.getDescription());
        issue.setInitialDate(issueDto.getInitialDate());
        issue.setCategory(issueDto.getCategory());
        issueDao.save(issue);
        LOGGER.debug("Updated " + issue.toString());

        return new IssueDto(issue);
    }

    public void deleteIssue(long id) {

        LOGGER.debug("Deleting issue by id " + id);
        issueDao.delete(id);
        LOGGER.debug("Deleted issue");
    }

    public Page<IssueDto> findAllByPage(Pageable pageable) {
        Page<Issue> issues = issueDao.findAll(pageable);
        Page<IssueDto> issueDtos = issues.map(new Converter<Issue, IssueDto>() {
            @Override
            public IssueDto convert(Issue issue) {
                return new IssueDto(issue);
            }
        });
        return issueDtos;
    }
}
