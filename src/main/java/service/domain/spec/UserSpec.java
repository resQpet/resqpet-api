package service.domain.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;
import service.domain.entity.user.User;
import service.domain.types.UserStatus;

import java.util.ArrayList;
import java.util.List;

import static service.domain.entity.BaseEntity.FIELD_ID;
import static service.domain.entity.user.User.FIELD_DOCUMENT;
import static service.domain.entity.user.User.FIELD_INFO;
import static service.domain.entity.user.User.FIELD_STATUS;
import static service.domain.entity.user.User.FIELD_USERNAME;
import static service.domain.entity.user.UserInfo.FIELD_FIRSTNAME;
import static service.domain.entity.user.UserInfo.FIELD_LASTNAME;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSpec extends BaseSpecification<User> {

    private Long id;
    private String username;
    private String document;
    private String lastname;
    private String firstname;
    private UserStatus status;

    @Override
    public Predicate toPredicate(@NonNull Root<User> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();
        // Agrega los campos necesarios
        super.add(predicates, root, builder, id, FIELD_ID); // Adds ID
        super.add(predicates, root, builder, status, FIELD_STATUS); // Adds phone
        super.addEquals(predicates, root, builder, username, FIELD_USERNAME); // adds username
        super.addEquals(predicates, root, builder, document, FIELD_DOCUMENT); // adds document
        super.add(predicates, root, builder, lastname, FIELD_INFO, FIELD_LASTNAME); // Adds lastname
        super.add(predicates, root, builder, firstname, FIELD_INFO, FIELD_FIRSTNAME); // Adds firstname
        // Convierte el predicado en un arreglo y retorna un AND.
        return builder.and(predicates.toArray(Predicate[]::new));
    }
}