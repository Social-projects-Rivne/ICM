package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueService {

    private static final Logger LOG = LogManager.getLogger(IssueService.class.getName());

    private IssueDao issueDao;

    @Autowired
    public IssueService(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    public List<IssueDto> findAll(){

        List<IssueDto> issueDto = new ArrayList<>();

        for(Issue issue : issueDao.findAllByOrderByIdAsc()){
            issueDto.add(new IssueDto(issue));
        }
        LOG.info("Show all issues");
        return issueDto;
    }

    public Issue findOne(long id){

        Issue issue = issueDao.findOne(id);

        if(issue == null)
            throw new IllegalStateException("Issue not found");
        return issue;
    }

    public IssueDto findById(long id) {

        Issue issue = findOne(id);

        LOG.info("Find issue by id " + issue.toString());
        return new IssueDto(issue);
    }

    public IssueDto addIssue(IssueDto issueDto){
        Issue issue = new Issue();

        issue.setUser(new User(issueDto.getUserDto()));
        issue.setTitle(issueDto.getTitle());
        issue.setDescription(issueDto.getDescription());
        issue.setLongitude(issueDto.getLongitude());
        issue.setLatitude(issueDto.getLatitude());
        issue.setInitialDate(issueDto.getInitialDate());
        issue.setCategory(issueDto.getCategory());

        issueDao.save(issue);

        LOG.info("Add " + issue.toString());
        return new IssueDto(issue);
    }

    public IssueDto editIssue(IssueDto issueDto){

        Issue issue = findOne(issueDto.getId());
        issue.setTitle(issueDto.getTitle());
        issue.setDescription(issueDto.getDescription());
        issue.setInitialDate(issueDto.getInitialDate());
        issue.setCategory(issueDto.getCategory());

        issueDao.save(issue);

        LOG.info("Edit " + issue.toString());
        return new IssueDto(issue);
    }

    public void deleteIssue(long id){

        Issue issue = findOne(id);

        issueDao.delete(issue);

        LOG.info("Delete " + issue.toString());
    }

}
