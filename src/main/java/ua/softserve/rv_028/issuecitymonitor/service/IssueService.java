package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueLocationDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.IssueMapper;

import java.time.LocalDateTime;
import java.util.List;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class IssueService {

    private static final Logger LOGGER = Logger.getLogger(IssueService.class);

    private IssueDao issueDao;

    private IssueMapper issueMapper;

    @Autowired
    public IssueService(IssueDao issueDao, IssueMapper issueMapper) {
        this.issueDao = issueDao;
        this.issueMapper = issueMapper;
    }

    public IssueDto addIssue(IssueDto issueDto){
        Issue issue = issueMapper.toEntity(issueDto);
        LOGGER.debug("Added issue " + issueDto);
        return issueMapper.toDto(issue);
    }

    public Page<IssueDto> findAllByPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");
        Page<Issue> issues = issueDao.findAll(pageRequest);
        LOGGER.debug("Found all issues");
        return issueMapper.toDtoPage(issues);
    }

    public IssueDto findById(long id) {
        Issue issue = findOne(id);
        LOGGER.debug("Found " + issue.toString());
        return issueMapper.toDto(issue);
    }

    public IssueDto update(IssueDto issueDto) {
        Issue issue = findOne(issueDto.getId());
        issue.setTitle(issueDto.getTitle());
        issue.setDescription(issueDto.getDescription());
        issue.setInitialDate(LocalDateTime.parse(issueDto.getInitialDate(), DATE_FORMAT));
        issue.setCategory(issueDto.getCategory());
        issue = issueDao.save(issue);
        LOGGER.debug("Updated " + issue.toString());
        return issueMapper.toDto(issue);
    }

    public void deleteById(long id) {
        issueDao.delete(id);
        LOGGER.debug("Deleted issue " + id);
    }

    private Issue findOne(long id) {
        Issue issue = issueDao.findOne(id);
        if (issue == null) {
            throw new IllegalArgumentException("issue id not found:" + id);
        }
        return issue;
    }

    public List<IssueLocationDto> findAll() {
        List<IssueLocationDto> issueLocationDtos = issueMapper.toLocationDtoList(issueDao.findAll());
        LOGGER.debug("Found all issue locations");
        return issueLocationDtos;
    }

}
