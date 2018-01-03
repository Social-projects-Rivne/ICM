package ua.softserve.rv_028.issuecitymonitor.service.specification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.service.specifiation.EventSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class SpecificationsTest {

    @Mock
    private CriteriaBuilder cb;

    @Mock
    private Root<Event> eventRoot;

    @Mock
    private CriteriaQuery<?> query;

    private static final Map<String, String>
            eventQueryMap = new HashMap<>(),
            issueQueryMap = new HashMap<>(),
            userQueryMap = new HashMap<>(),
            petitionQueryMap = new HashMap<>();

    @Test
    public void testEventSpecification() {
        eventQueryMap.put("ass", "hole");
        EventSpecification eventSpecification = new EventSpecification(eventQueryMap);
    }
}
