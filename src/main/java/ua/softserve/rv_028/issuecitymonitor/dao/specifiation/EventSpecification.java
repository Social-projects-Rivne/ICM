package ua.softserve.rv_028.issuecitymonitor.dao.specifiation;

import org.springframework.data.jpa.domain.Specification;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import javax.persistence.criteria.*;

public class EventSpecification implements Specification<Event> {

    private String key;
    private String value;

    public EventSpecification(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Path<String> actualValue = root.get(key);

        if(value==null) {
            return null;
        }

        if(key.equals("category")) {
            return criteriaBuilder.equal(actualValue, EventCategory.valueOf(value));
        }

        return criteriaBuilder.like(criteriaBuilder.lower(actualValue), ("%"+value+"%").toLowerCase());
    }
}
