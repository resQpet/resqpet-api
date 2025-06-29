package service.domain.entity.foundation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import service.domain.entity.BaseEntity;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "foundation_type")
public class FoundationType extends BaseEntity {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";

    @Size(max = 100)
    @Column(name = "NAME", length = 100)
    private String name;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

}