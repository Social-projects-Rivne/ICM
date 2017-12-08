package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.controller.IssueController;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.exception.IssueNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class IssueService {

    private static final Logger LOG = Logger.getLogger(IssueController.class.getName());

    private IssueDao issueDao;

    private UserDao userDao;

    @Autowired
    public IssueService(IssueDao issueDao, UserDao userDao) {
        this.issueDao = issueDao;
        this.userDao = userDao;
    }

    public IssueDto addIssue(IssueDto dto){
        Issue issue = new Issue();

        issue.setUser(new User(dto.getUserDto()));
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setLongitude(dto.getLongitude());
        issue.setLatitude(dto.getLatitude());
        issue.setInitialDate(dto.getInitialDate());
        issue.setCategory(dto.getCategory());

        issue = issueDao.save(issue);
        LOG.info("add " + issue.toString());
        return new IssueDto(issue);
    }

    public IssueDto editIssue(IssueDto dto, Long id){
        Issue issue = issueDao.findOne(id);

        issue.setUser(userDao.findOne(dto.getUserDto().getId()));
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setLongitude(dto.getLongitude());
        issue.setLatitude(dto.getLatitude());
        issue.setInitialDate(dto.getInitialDate());
        issue.setCategory(dto.getCategory());

        issue = issueDao.save(issue);
        LOG.info("edit " + issue.toString());
        return new IssueDto(issue);
    }

    public void deleteIssue(long id) throws IssueNotFoundException{
        Issue issue = issueDao.findOne(id);
        if(issue != null){
            issueDao.delete(issue);
            LOG.info("delete " + issue.toString());
        } else {throw new IssueNotFoundException("Issue not found");}
    }

    public List<IssueDto> findAll(){

        List<IssueDto> issueDto = new ArrayList<>();

        for(Issue issue : issueDao.findAll()){
            issueDto.add(new IssueDto(issue));
        }
        LOG.info("show all issues");
        return issueDto;
    }

}
