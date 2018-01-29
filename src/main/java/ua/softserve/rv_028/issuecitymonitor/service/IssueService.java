package ua.softserve.rv_028.issuecitymonitor.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
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
@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class IssueService {

    IssueDao issueDao;

    IssueMapper issueMapper;

    public IssueDto addIssue(IssueDto issueDto){
        Issue issue = issueMapper.toEntity(issueDto);
        log.debug("Added issue " + issueDto);
        return issueMapper.toDto(issue);
    }

    public Page<IssueDto> findAllByPage(int pageNumber, int pageSize, Sort.Direction direction, String columns) {
        String[] columnArray = columns.split(",");
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, direction, columnArray);
        Page<Issue> issues = issueDao.findAll(pageRequest);
        log.debug("Found all issues");
        return issueMapper.toDtoPage(issues);
    }

    public IssueDto findById(long id) {
        Issue issue = findOne(id);
        log.debug("Found " + issue.toString());
        return issueMapper.toDto(issue);
    }

    public IssueDto update(IssueDto issueDto) {
        Issue issue = findOne(issueDto.getId());
        issue.setTitle(issueDto.getTitle());
        issue.setDescription(issueDto.getDescription());
        issue.setInitialDate(LocalDateTime.parse(issueDto.getInitialDate(), DATE_FORMAT));
        issue.setCategory(issueDto.getCategory());
        issue = issueDao.save(issue);
        log.debug("Updated " + issue.toString());
        return issueMapper.toDto(issue);
    }

    public void deleteById(long id) {
        issueDao.delete(id);
        log.debug("Deleted issue " + id);
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
        log.debug("Found all issue locations");
        return issueLocationDtos;
    }

    public String getPathToImg() {

        String downloadPath = "../../scss/Images/";
        log.debug("Found path" + downloadPath);
        return downloadPath;
    }
}
