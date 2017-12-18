package ua.softserve.rv_028.issuecitymonitor.service.specifiation;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

public class EventSpecification implements Specification<Event> {

    private static final Logger LOGGER = Logger.getLogger(EventSpecification.class);

    private String key;
    private String value;

    public EventSpecification(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        if(value==null || value.equals("")) {
            return null;
        }
        //TODO DATES
        /*if(key.equals("fromDate")) {
            try {
                return criteriaBuilder.greaterThanOrEqualTo(,
                        new SimpleDateFormat("dd/MM/yyyy").parse(value));
            } catch (ParseException e) {
                LOGGER.debug("error parsing date");
                return null;
            }
        }*/

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
