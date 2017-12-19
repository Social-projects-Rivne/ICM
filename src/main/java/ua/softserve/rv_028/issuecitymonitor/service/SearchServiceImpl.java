package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserMapper userMapper;
    private final EventMapper eventMapper;
    private final IssueMapper issueMapper;

    @Autowired
    public SearchServiceImpl(EventDao eventDao, UserMapper userMapper, UserDao userDao, IssueDao issueDao,
                             PetitionDao petitionDao, EventMapper eventMapper, IssueMapper issueMapper) {
        this.eventDao = eventDao;
        this.userMapper = userMapper;
        this.userDao = userDao;
        this.issueDao = issueDao;
        this.petitionDao = petitionDao;
        this.eventMapper = eventMapper;
        this.issueMapper = issueMapper;
    }

    @Override
    public List<EventDto> findEventsByCriteria(Map<String, String> queryMap) {
        Specifications<Event> specifications = getEventSpecifications(queryMap);

        List<EventDto> events = eventMapper.toDtoList(eventDao.findAll(specifications));

        LOGGER.debug("found events "+events.toString());
        return events;
    }

    @Override
    public List<UserDto> findUsersByCriteria(Map<String, String> queryMap) {
        return null;
    }

    @Override
    public List<IssueDto> findIssuesByCriteria(Map<String, String> queryMap) {
        return null;
    }

    private Specifications<Event> getEventSpecifications(Map<String, String> queryMap) {
        Specifications<Event> specifications = Specifications.where(null);
        for(Map.Entry<String, String> entry : queryMap.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                specifications = specifications.and(new EventSpecification(entry.getKey(), entry.getValue()));
            }
        }
        return specifications;
    }


}
