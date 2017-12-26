package ua.softserve.rv_028.issuecitymonitor.service.specifiation;

import org.springframework.data.jpa.domain.Specification;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserSpecification implements Specification<User> {

    private final Map<String, String> queryMap;

    public UserSpecification(Map<String, String> queryMap){
        this.queryMap = queryMap;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        for(Map.Entry<String, String> entry : queryMap.entrySet()) {

            String value = entry.getValue();
            String key = entry.getKey();

            if(!value.isEmpty()) {
                if(value.replace(" ", "").equals("")) {
                    continue;
                }

                if (key.equals("fullName")) {
                    String[] values = value.split(" ", 2);

                    predicates.add(cb.or(
                            cb.like(cb.lower(root.get("firstName")), ("%" + values[0] + "%").toLowerCase()),
                            cb.like(cb.lower(root.get("lastName")), ("%" + values[values.length == 1 ? 0 : 1] + "%").toLowerCase())));
                }
            }
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}