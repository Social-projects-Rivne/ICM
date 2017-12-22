package ua.softserve.rv_028.issuecitymonitor.service.specifiation;

import org.springframework.data.jpa.domain.Specification;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

public class EventSpecification implements Specification<Event> {

    private String key;
    private String value;

    public EventSpecification(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        if(value==null || value.replace(" ", "").equals("")) {
            return null;
        }

        if(key.equals("fromDate")) {
            return criteriaBuilder.or(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("initialDate"),
                            LocalDateTime.parse(value, DATE_FORMAT)),
                    criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"),
                            LocalDateTime.parse(value, DATE_FORMAT)));
        }

        if(key.equals("toDate")) {
            return criteriaBuilder.or(
                    criteriaBuilder.lessThanOrEqualTo(root.get("initialDate"),
                            LocalDateTime.parse(value, DATE_FORMAT)),
                    criteriaBuilder.lessThanOrEqualTo(root.get("endDate"),
                            LocalDateTime.parse(value, DATE_FORMAT)));
        }

        if(key.equals("user")) {
            String[] values = value.split(" ",2);

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("firstName")), ("%"+values[0]+"%").toLowerCase()),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("lastName")), ("%"+values[values.length == 1 ? 0 : 1]+"%").toLowerCase()));
        }

        if(key.equals("text")) {
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), ("%"+value+"%").toLowerCase()),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), ("%"+value+"%").toLowerCase()));
        }

        if(key.equals("category")) {
            if(value.equals("ANY"))
                return null;
            return criteriaBuilder.equal(root.get(key), EventCategory.valueOf(value));
        }

        return null;
    }
}
