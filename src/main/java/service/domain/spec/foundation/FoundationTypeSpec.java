package service.domain.spec.foundation;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import service.domain.entity.foundation.FoundationType;
import service.domain.spec.BaseSpecification;

import java.util.ArrayList;
import java.util.List;

import static service.domain.entity.foundation.FoundationType.FIELD_NAME;

@Data
@EqualsAndHashCode(callSuper = true)
public class FoundationTypeSpec extends BaseSpecification<FoundationType> {

    private String name;

    @Override
    public Predicate toPredicate(@NonNull Root<FoundationType> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        super.addEmpty(predicates, root, criteriaBuilder, name, FIELD_NAME);
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
