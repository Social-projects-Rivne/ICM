package ua.softserve.rv_028.issuecitymonitor.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dao.specifiation.EventSpecification;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{

    private final static Logger LOGGER = Logger.getLogger(SearchServiceImpl.class.getName());

    private final EventDao eventDao;

    @Autowired
    public SearchServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public List<EventDto> findByCriteria(Map<String, String> queryMap) {
        List<EventDto> events = new ArrayList<>();
        Specifications<Event> specifications = Specifications.where(null);
        for(Map.Entry<String, String> entry : queryMap.entrySet()){
            specifications = specifications.and(new EventSpecification(entry.getKey(), entry.getValue()));
        }
        for(Event e : eventDao.findAll(specifications)) {
            events.add(new EventDto(e));
        }
        LOGGER.debug(events.toString());
        return events;
    }
}
