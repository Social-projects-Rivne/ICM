package ua.softserve.rv_028.issuecitymonitor.dao.specifiation;

import org.springframework.data.jpa.domain.Specification;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EventSpecification implements Specification<Event> {

    private String key;
    private String value;

    public EventSpecification(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        if(value==null || key.equals("")) {
            return null;
        }

        if(key.equals("text")){
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), ("%"+value+"%").toLowerCase()),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), ("%"+value+"%").toLowerCase()));
        }

        if(key.equals("category")) {
            return criteriaBuilder.equal(root.get(key), EventCategory.valueOf(value));
        }

        return null;
    }
}
