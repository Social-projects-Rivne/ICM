package ua.softserve.rv_028.issuecitymonitor.service;

import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    private MapperService mapperService;

    @Autowired
    public IssueService(IssueDao issueDao, MapperService mapperService) {
        this.issueDao = issueDao;
        this.mapperService = mapperService;
    }

    public List<IssueDto> findAll(){
        LOGGER.debug("Finding all issues");
        List<IssueDto> issueDto;

        issueDto = mapperService.<IssueDto>toDtoList(issueDao.findAllByOrderByIdAsc());
        LOGGER.debug("Found all issues");
        return issueDto;
    }

    public Issue findOne(long id){

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

    public IssueDto addIssue(IssueDto issueDto){
        Issue issue = new Issue();
        LOGGER.debug("Adding " + issue.toString());
        issue.setUser(new User(issueDto.getUserDto()));
        issue.setTitle(issueDto.getTitle());
        issue.setDescription(issueDto.getDescription());
        issue.setLongitude(issueDto.getLongitude());
        issue.setLatitude(issueDto.getLatitude());
        issue.setInitialDate(issueDto.getInitialDate());
        issue.setCategory(issueDto.getCategory());
        LOGGER.debug("Added " + issue.toString());
        issueDao.save(issue);


        return new IssueDto(issue);
    }

    public IssueDto editIssue(IssueDto issueDto){

        Issue issue = findOne(issueDto.getId());
        LOGGER.debug("Edit " + issue.toString());
        issue.setTitle(issueDto.getTitle());
        issue.setDescription(issueDto.getDescription());
        issue.setInitialDate(issueDto.getInitialDate());
        issue.setCategory(issueDto.getCategory());

        issueDao.save(issue);
        LOGGER.debug("Updated " + issue.toString());

        return new IssueDto(issue);
    }

    public void deleteIssue(long id){

        Issue issue = findOne(id);
        LOGGER.debug("Deleting " + issue.toString());
        issueDao.delete(issue);
        LOGGER.debug("Deleted " + issue.toString());
    }

}
