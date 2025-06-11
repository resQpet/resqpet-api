package service.domain.spec;

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
public class RoleSpec extends BaseSpecification<Role> {

    private Long id;
    private String name;
    private String description;

    @Override
    public Predicate toPredicate(@NonNull Root<Role> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();
        // Agrega los campos necesarios
        super.add(predicates, root, builder, id, FIELD_ID); // Adds ID
        super.add(predicates, root, builder, name, FIELD_NAME); // Adds ID
        super.add(predicates, root, builder, description, FIELD_DESCRIPTION); // Adds ID
        // Convierte el predicado en un arreglo y retorna un AND.
        return builder.and(predicates.toArray(Predicate[]::new));
    }
}