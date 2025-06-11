package service.domain.spec.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;
import service.domain.exceptions.InternalErrorException;
import service.domain.spec.BaseSpecification;

import java.util.Objects;

import static service.domain.entity.BaseEntity.FIELD_ID;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class SearchSpecification<T> extends BaseSpecification<T> {

    protected Long id;
    protected String term;

    protected abstract String getDefaultOrderField();

    /**
     * Check and apply ordering for the result based on the presence of an ID value.
     *
     * @param root    The root entity to build the query from
     * @param query   The criteria query to be modified with ordering
     * @param builder The criteria builder to build predicates and ordering
     */
    protected void checkForResultOrdering(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (Objects.nonNull(getId())) {
            query.orderBy(builder.desc(builder.equal(root.get(FIELD_ID), getId())), builder.asc(root.get(getDefaultOrderField())));
        } else {
            query.orderBy(builder.asc(root.get(getDefaultOrderField())));
        }
    }

}
