package com.example.ms.user.service.specification;

import com.example.ms.user.dao.entity.UserEntity;
import com.example.ms.user.model.criteria.UserCriteria;
import com.example.ms.user.util.PredicateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static com.example.ms.user.dao.entity.UserEntity.Fields.age;
import static com.example.ms.user.util.PredicateUtil.applyLikePattern;

@AllArgsConstructor
public class UserSpecification implements Specification<UserEntity> {
    private UserCriteria userCriteria;

    @Override
    public Predicate toPredicate(Root<UserEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        var predicates = PredicateUtil.builder()
                .addNullSafety(userCriteria.getAgeFrom(),
                        ageFrom -> criteriaBuilder.greaterThanOrEqualTo(root.get(age), ageFrom))
                .addNullSafety(userCriteria.getAgeTo(),
                        ageTo-> criteriaBuilder.lessThanOrEqualTo(root.get(age), ageTo))
                .addNullSafety(userCriteria.getAge(),
                        age-> criteriaBuilder.equal(root.get(UserEntity.Fields.age), age))
                .addNullSafety(userCriteria.getFirstName(),
                        firstName-> criteriaBuilder.like(root.get(UserEntity.Fields.firstName), applyLikePattern(firstName)))
                .addNullSafety(userCriteria.getStatus(),
                        status-> criteriaBuilder.like(root.get(UserEntity.Fields.status), applyLikePattern(String.valueOf(status))))
                .build();

        return criteriaBuilder.and(predicates);
    }
}
