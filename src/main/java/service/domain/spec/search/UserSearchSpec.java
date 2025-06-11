package service.domain.spec.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import service.domain.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import static service.domain.entity.BaseEntity.FIELD_ID;
import static service.domain.entity.user.User.FIELD_DOCUMENT;
import static service.domain.entity.user.User.FIELD_INFO;
import static service.domain.entity.user.User.FIELD_USERNAME;
import static service.domain.entity.user.UserInfo.FIELD_FIRSTNAME;
import static service.domain.entity.user.UserInfo.FIELD_LASTNAME;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSearchSpec extends SearchSpecification<User> {

    @Override
    public Predicate toPredicate(@NonNull Root<User> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        // Añade los términos vacíos al predicado.
        super.add(predicates, root, criteriaBuilder, id, FIELD_ID);
        super.addEmpty(predicates, root, criteriaBuilder, term, FIELD_USERNAME);
        super.addEmpty(predicates, root, criteriaBuilder, term, FIELD_DOCUMENT);
        super.addEmpty(predicates, root, criteriaBuilder, term, FIELD_INFO, FIELD_FIRSTNAME);
        super.addEmpty(predicates, root, criteriaBuilder, term, FIELD_INFO, FIELD_LASTNAME);
        // Convierte el predicado en un arreglo y retorna un AND.
        final Predicate predicate = criteriaBuilder.or(predicates.toArray(Predicate[]::new));

        this.checkForResultOrdering(root, query, criteriaBuilder);

        return criteriaBuilder.and(predicate);
    }

    @Override
    protected String getDefaultOrderField() {
        return FIELD_ID;
    }
}
