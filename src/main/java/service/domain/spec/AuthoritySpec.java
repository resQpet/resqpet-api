package service.domain.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import service.domain.entity.auth.Authority;

import java.util.ArrayList;
import java.util.List;

import static service.domain.entity.auth.Authority.FIELD_DESCRIPTION;
import static service.domain.entity.auth.Authority.FIELD_NAME;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthoritySpec extends BaseSpecification<Authority> {

    private String name;
    private String description;

    @Override
    public Predicate toPredicate(@NonNull Root<Authority> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        super.addEmpty(predicates, root, criteriaBuilder, name, FIELD_NAME);
        super.addEmpty(predicates, root, criteriaBuilder, description, FIELD_DESCRIPTION);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
