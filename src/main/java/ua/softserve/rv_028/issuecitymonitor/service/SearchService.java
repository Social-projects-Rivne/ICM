package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.IssueDao;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.EventMapper;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.IssueMapper;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.UserMapper;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.EventSpecification;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.IssueSpecification;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.UserSpecification;

import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private final static Logger LOGGER = Logger.getLogger(SearchService.class.getName());

    private final EventDao eventDao;
    private final UserDao userDao;
    private final IssueDao issueDao;
    private final PetitionDao petitionDao;

    private final UserMapper userMapper;
    private final EventMapper eventMapper;
    private final IssueMapper issueMapper;

    public SearchService(EventDao eventDao, UserMapper userMapper, UserDao userDao, IssueDao issueDao,
                             PetitionDao petitionDao, EventMapper eventMapper, IssueMapper issueMapper) {
        this.eventDao = eventDao;
        this.userMapper = userMapper;
        this.userDao = userDao;
        this.issueDao = issueDao;
        this.petitionDao = petitionDao;
        this.eventMapper = eventMapper;
        this.issueMapper = issueMapper;
    }

    public Page<EventDto> findEventsByCriteria(Map<String, String> queryMap, int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        Page<EventDto> eventDtos = eventDao.findAll(getEventSpecifications(queryMap), pageRequest).map(eventMapper::toDto);
        LOGGER.debug("Events search successful");
        return eventDtos;
    }

    public Page<UserDto> findUsersByCriteria(Map<String, String> queryMap, int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        Page<UserDto> userDtos = userDao.findAll(getUserSpecifications(queryMap), pageRequest).map(userMapper::toDto);
        LOGGER.debug("Users search successful");
        return userDtos;
    }

    public Page<IssueDto> findIssuesByCriteria(Map<String, String> queryMap, int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        Page<IssueDto> issueDtos = issueDao.findAll(getIssueSpecifications(queryMap), pageRequest).map(issueMapper::toDto);
        LOGGER.debug("Issues search successful");
        return issueDtos;
    }

    private Specifications<Event> getEventSpecifications(Map<String, String> queryMap) {
        Specifications<Event> specifications = Specifications.where(null);
        for(Map.Entry<String, String> entry : queryMap.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                Specification<Event> specification = new EventSpecification(entry.getKey(), entry.getValue());
                specifications = specifications.and(specification);
            }
        }
        return specifications;
    }

    private Specifications<Issue> getIssueSpecifications(Map<String, String> queryMap) {
        Specifications<Issue> specifications = Specifications.where(null);
        for(Map.Entry<String, String> entry : queryMap.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                Specification<Issue> specification = new IssueSpecification(entry.getKey(), entry.getValue());
                specifications = specifications.and(specification);
            }
        }
        return specifications;
    }

    private Specifications<User> getUserSpecifications(Map<String, String> queryMap) {
        Specifications<User> specifications = Specifications.where(null);
        for(Map.Entry<String, String> entry : queryMap.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                Specification<User> specification = new UserSpecification(entry.getKey(), entry.getValue());
                specifications = specifications.and(specification);
            }
        }
        return specifications;
    }



}
