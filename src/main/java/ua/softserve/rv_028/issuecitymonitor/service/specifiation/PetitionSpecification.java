package ua.softserve.rv_028.issuecitymonitor.service.specifiation;

import org.springframework.data.jpa.domain.Specification;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

public class PetitionSpecification implements Specification<Petition> {

    private final Map<String, String> queryMap;

    public PetitionSpecification(Map<String, String> queryMap){
        this.queryMap = queryMap;
    }

    @Override
    public Predicate toPredicate(Root<Petition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        for(Map.Entry<String, String> entry : queryMap.entrySet()) {

            String value = entry.getValue();
            String key = entry.getKey();

            if(!value.isEmpty()) {
                if(value.replace(" ", "").equals("")) {
                    continue;
                }

                if(key.equals("fromDate")) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("initialDate"),
                                    LocalDateTime.parse(value, DATE_FORMAT)));
                    continue;
                }

                if(key.equals("toDate")) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("initialDate"),
                                    LocalDateTime.parse(value, DATE_FORMAT)));
                    continue;
                }

                if(key.equals("user")) {
                    String[] values = value.split(" ",2);

                    predicates.add(cb.or(
                            cb.like(cb.lower(root.get("user").get("firstName")), ("%"+values[0]+"%").toLowerCase()),
                            cb.like(cb.lower(root.get("user").get("lastName")), ("%"+values[values.length == 1 ? 0 : 1]+"%").toLowerCase())));
                    continue;
                }

                if(key.equals("text")) {
                    predicates.add(cb.or(
                            cb.like(cb.lower(root.get("title")), ("%"+value+"%").toLowerCase()),
                            cb.like(cb.lower(root.get("description")), ("%"+value+"%").toLowerCase())));
                    continue;
                }

                if(key.equals("category")) {
                    if(!value.equals("ANY")) {
                        predicates.add(cb.equal(root.get(key), EventCategory.valueOf(value)));
                    }
                }
            }

        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
