package ua.softserve.rv_028.issuecitymonitor.service;

import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;

import java.util.List;
import java.util.Map;

public interface SearchService {
    List<EventDto> findByCriteria(Map<String, String> queryMap);
}
