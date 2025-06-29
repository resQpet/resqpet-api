package service.domain.spec.search.foundation;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jetbrains.annotations.NotNull;
import service.domain.entity.foundation.FoundationType;
import service.domain.spec.search.SearchSpecification;

import java.util.ArrayList;
import java.util.List;

import static service.domain.entity.foundation.FoundationType.FIELD_DESCRIPTION;
import static service.domain.entity.foundation.FoundationType.FIELD_NAME;

public class FoundationTypeSearchSpec extends SearchSpecification<FoundationType> {

    @Override
    protected String getDefaultOrderField() {
        return FIELD_NAME;
    }

    @Override
    public Predicate toPredicate(@NotNull Root<FoundationType> root, CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        super.addEmpty(predicates, root, criteriaBuilder, term, FIELD_NAME);
        super.addEmpty(predicates, root, criteriaBuilder, term, FIELD_DESCRIPTION);
        // Convierte el predicado en un arreglo y retorna un AND.
        final Predicate predicate = criteriaBuilder.or(predicates.toArray(Predicate[]::new));
        // Loads specification filter orderBy
        this.checkForResultOrdering(root, query, criteriaBuilder);
        return criteriaBuilder.and(predicate);
    }
}
