package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.IssueMapper;

import java.time.LocalDateTime;
import java.util.List;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Service
public class IssueService {

    private static final Logger LOGGER = Logger.getLogger(IssueService.class.getName());

    private IssueDao issueDao;

    private IssueMapper issueMapper;

    @Autowired
    public IssueService(IssueDao issueDao, IssueMapper issueMapper) {
        this.issueDao = issueDao;
        this.issueMapper = issueMapper;
    }

    public List<IssueDto> findAll(){
        LOGGER.debug("Finding all issues");
        List<IssueDto> issueDto = issueMapper.toDtoList(issueDao.findAllByOrderByIdAsc());
        LOGGER.debug("Found all issues");
        return issueDto;
    }

    public IssueDto addIssue(IssueDto issueDto){
        LOGGER.debug("Adding issue");
        Issue issue = issueMapper.toEntity(issueDto);
        LOGGER.debug("Added issue " + issueDto);
        return issueMapper.toDto(issue);
    }

    private Issue findOne(long id){
        Issue issue = issueDao.findOne(id);
        if(issue == null){
            throw new IllegalStateException("issue id not found:" + id);
        }
        return issue;
    }

    public IssueDto findById(long id) {
        LOGGER.debug("Finding issue by id" + id);
        Issue issue = findOne(id);
        LOGGER.debug("Found by id " + issue.toString());
        return issueMapper.toDto(issue);
    }

    public IssueDto editIssue(IssueDto issueDto){
        Issue issue = findOne(issueDto.getId());
        issue.setTitle(issueDto.getTitle());
        issue.setDescription(issueDto.getDescription());
        issue.setInitialDate(LocalDateTime.parse(issueDto.getInitialDate(), DATE_FORMAT));
        issue.setCategory(issueDto.getCategory());

        LOGGER.debug("Updating " + issue.toString());
        issueDao.save(issue);
        LOGGER.debug("Updated " + issue.toString());

        return issueMapper.toDto(issue);
    }

    public void deleteIssue(long id){
        Issue issue = findOne(id);
        LOGGER.debug("Deleting " + issue.toString());
        issueDao.delete(issue);
        LOGGER.debug("Deleted " + issue.toString());
    }

}
