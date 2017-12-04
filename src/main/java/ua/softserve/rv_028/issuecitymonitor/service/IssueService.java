package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IssueService {

    @Autowired
    private IssueDao issueDao;

    public IssueDto addIssue(IssueDto dto){
        issueDao.save(new Issue(dto));
        return dto;
    }

    public List<IssueDto> findAll(){
        List<Issue> all = issueDao.findAll();
        return all.stream().map(IssueDto::new).collect(Collectors.toList());
    }


}
