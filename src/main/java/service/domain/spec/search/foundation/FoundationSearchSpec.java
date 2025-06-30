package service.domain.spec.search.foundation;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jetbrains.annotations.NotNull;
import service.domain.entity.foundation.Foundation;
import service.domain.spec.search.SearchSpecification;

import java.util.ArrayList;
import java.util.List;

import static service.domain.entity.foundation.Foundation.FIELD_NAME;

public class FoundationSearchSpec extends SearchSpecification<Foundation> {

    @Override
    protected String getDefaultOrderField() {
        return FIELD_NAME;
    }

    @Override
    public Predicate toPredicate(@NotNull Root<Foundation> root, CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        super.addEmpty(predicates, root, criteriaBuilder, term, FIELD_NAME);

        this.checkForResultOrdering(root, query, criteriaBuilder);
        final Predicate predicate = criteriaBuilder.or(predicates.toArray(Predicate[]::new));
        return criteriaBuilder.and(predicate);
    }
}
