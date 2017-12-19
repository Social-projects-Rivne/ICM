package ua.softserve.rv_028.issuecitymonitor.service.specifiation;

import org.springframework.data.jpa.domain.Specification;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification implements Specification<User> {

    private String key;
    private String value;

    public UserSpecification(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        if(value==null || value.replace(" ", "").equals("")) {
            return null;
        }

        if(key.equals("fullName")) {
            String[] values = value.split(" ",2);

            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), ("%"+values[0]+"%").toLowerCase()),
                    cb.like(cb.lower(root.get("lastName")), ("%"+values[values.length == 1 ? 0 : 1]+"%").toLowerCase()));
        }

        return null;
    }
}
