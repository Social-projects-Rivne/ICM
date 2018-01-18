package ua.softserve.rv_028.issuecitymonitor.service.specifiation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserSpecification implements Specification<User> {

    Map<String, String> queryMap;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        for(Map.Entry<String, String> entry : queryMap.entrySet()) {

            String value = entry.getValue();
            String key = entry.getKey();

            if(!value.isEmpty()) {

                if (key.equals("name")) {
                    String[] fullName = value.split(" ", 2);

                    predicates.add(cb.or(
                            cb.like(cb.lower(root.get("firstName")), ("%" + fullName[0] + "%").toLowerCase()),
                            cb.like(cb.lower(root.get("lastName")), ("%" + fullName[fullName.length == 1 ? 0 : 1] + "%").toLowerCase())));
                    continue;
                }
                if (key.equals("email")) {
                    predicates.add(cb.like(cb.lower(root.get("username")), ("%" + value + "%").toLowerCase()));
                    continue;
                }
                if (key.equals("deleted") && value.equals("false")) {
                    predicates.add(cb.notEqual(root.get("userStatus"), UserStatus.DELETED));
                }
            }
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}