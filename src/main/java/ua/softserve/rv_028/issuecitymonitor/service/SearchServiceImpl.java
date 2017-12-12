package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.rv_028.issuecitymonitor.dao.EventDao;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;

import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{

    private final EventDao eventDao;

    @Autowired
    public SearchServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public List<EventDto> findByCriteria(Map<String, String> queryMap) {
        return null;
    }
}
