package ua.softserve.rv_028.issuecitymonitor.service.specifiation;

import org.springframework.data.jpa.domain.Specification;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.IssueCategory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

public class IssueSpecification implements Specification<Issue> {

    private String key;
    private String value;

    public IssueSpecification(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        if(value==null || value.replace(" ", "").equals("")) {
            return null;
        }

        if(key.equals("fromDate")) {
            value += " 00:00";
            return cb.greaterThanOrEqualTo(root.get("initialDate"),
                            LocalDateTime.parse(value, DATE_FORMAT));
        }

        if(key.equals("toDate")) {
            value += " 00:00";
            return cb.lessThanOrEqualTo(root.get("initialDate"),
                            LocalDateTime.parse(value, DATE_FORMAT));
        }

        if(key.equals("user")) {
            String[] values = value.split(" ",2);

            return cb.or(
                    cb.like(cb.lower(root.get("user").get("firstName")), ("%"+values[0]+"%").toLowerCase()),
                    cb.like(cb.lower(root.get("user").get("lastName")), ("%"+values[values.length == 1 ? 0 : 1]+"%").toLowerCase()));
        }

        if(key.equals("text")) {
            return cb.or(
                    cb.like(cb.lower(root.get("title")), ("%"+value+"%").toLowerCase()),
                    cb.like(cb.lower(root.get("description")), ("%"+value+"%").toLowerCase()));
        }

        if(key.equals("category")) {
            if(value.equals("ANY"))
                return null;
            return cb.equal(root.get(key), IssueCategory.valueOf(value));
        }

        return null;
    }
}
