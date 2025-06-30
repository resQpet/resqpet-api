package service.domain.spec.foundation;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import service.domain.entity.foundation.Foundation;
import service.domain.spec.BaseSpecification;
import service.domain.types.FoundationStatus;

import java.util.ArrayList;
import java.util.List;

import static service.domain.entity.foundation.Foundation.FIELD_NAME;
import static service.domain.entity.foundation.Foundation.FIELD_STATUS;

@Data
@EqualsAndHashCode(callSuper = true)
public class FoundationSpec extends BaseSpecification<Foundation> {

    private String name;
    private FoundationStatus status;

    @Override
    public Predicate toPredicate(@NotNull Root<Foundation> root, CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        super.addEmpty(predicates, root, criteriaBuilder, name, FIELD_NAME);
        super.add(predicates, root, criteriaBuilder, status, FIELD_STATUS);
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
