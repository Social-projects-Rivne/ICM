package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.EventSpecification;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.IssueSpecification;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.UserSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{

    private final static Logger LOGGER = Logger.getLogger(SearchServiceImpl.class.getName());

    private final EventDao eventDao;
    private final UserDao userDao;
    private final IssueDao issueDao;
    private final PetitionDao petitionDao;

    private final MapperService mapperService;

    @Autowired
    public SearchServiceImpl(EventDao eventDao, MapperService mapperService, UserDao userDao, IssueDao issueDao, PetitionDao petitionDao) {
        this.eventDao = eventDao;
        this.mapperService = mapperService;
        this.userDao = userDao;
        this.issueDao = issueDao;
        this.petitionDao = petitionDao;
    }

    @Override
    public List<EventDto> findEventsByCriteria(Map<String, String> queryMap) {
        List<EventDto> events = new ArrayList<>();
        Specifications<Event> specifications = Specifications.where(null);
        boolean isWrappedSpecificationsNull = true;

        for(Map.Entry<String, String> entry : queryMap.entrySet()) {
            if(!entry.getKey().isEmpty() && !entry.getValue().isEmpty()) {
                specifications = specifications.and(new EventSpecification(entry.getKey(), entry.getValue()));
                isWrappedSpecificationsNull = false;
            }
            else {
                LOGGER.debug("skipping empty key or value");
            }
        }

        if(!isWrappedSpecificationsNull) {
            events = mapperService.toDtoList(eventDao.findAll(specifications));
        }

        LOGGER.debug("found events "+events.toString());
        return events;
    }

    @Override
    public List<UserDto> findUsersByCriteria(Map<String, String> queryMap) {
        List<UserDto> users = new ArrayList<>();
        Specifications<User> specifications = Specifications.where(null);
        boolean isWrappedSpecificationsNull = true;

        for(Map.Entry<String, String> entry : queryMap.entrySet()) {
            if(!entry.getKey().isEmpty() && !entry.getValue().isEmpty()) {
                specifications = specifications.and(new UserSpecification(entry.getKey(), entry.getValue()));
                isWrappedSpecificationsNull = false;
            }
            else {
                LOGGER.debug("skipping empty key or value");
            }
        }

        if(!isWrappedSpecificationsNull) {
            users = mapperService.toDtoList(userDao.findAll(specifications));
        }

        LOGGER.debug("found users "+users.toString());
        return users;
    }

    @Override
    public List<IssueDto> findIssuesByCriteria(Map<String, String> queryMap) {
        List<IssueDto> issues = new ArrayList<>();
        Specifications<Issue> specifications = Specifications.where(null);
        boolean isWrappedSpecificationsNull = true;

        for(Map.Entry<String, String> entry : queryMap.entrySet()) {
            if(!entry.getKey().isEmpty() && !entry.getValue().isEmpty()) {
                specifications = specifications.and(new IssueSpecification(entry.getKey(), entry.getValue()));
                isWrappedSpecificationsNull = false;
            }
            else {
                LOGGER.debug("skipping empty key or value");
            }
        }

        if(!isWrappedSpecificationsNull) {
            issues = mapperService.toDtoList(issueDao.findAll(specifications));
        }

        LOGGER.debug("found issues "+issues.toString());
        return issues;
    }
}
