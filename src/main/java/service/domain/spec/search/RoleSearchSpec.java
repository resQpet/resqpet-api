package service.domain.spec.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;
import service.domain.entity.auth.Role;

import java.util.ArrayList;
import java.util.List;

import static service.domain.entity.BaseEntity.FIELD_ID;
import static service.domain.entity.auth.Role.FIELD_DESCRIPTION;
import static service.domain.entity.auth.Role.FIELD_NAME;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleSearchSpec extends SearchSpecification<Role> {

    @Override
    public Predicate toPredicate(@NonNull Root<Role> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();
        // Agrega los campos necesarios
        super.add(predicates, root, builder, id, FIELD_ID);
        super.addEmpty(predicates, root, builder, term, FIELD_NAME); // Adds ID
        super.addEmpty(predicates, root, builder, term, FIELD_DESCRIPTION); // Adds ID
        // Convierte el predicado en un arreglo y retorna un AND.
        final Predicate predicate = builder.or(predicates.toArray(Predicate[]::new));
        // Loads specification filter orderBy
        this.checkForResultOrdering(root, query, builder);
        // Returns the resulting predicate
        return builder.and(predicate);
    }

    @Override
    protected String getDefaultOrderField() {
        return FIELD_DESCRIPTION;
    }
}