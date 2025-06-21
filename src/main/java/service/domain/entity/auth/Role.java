package service.domain.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import service.domain.entity.BaseEntity;

import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role extends BaseEntity {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_ORGANIZATION = "organization";
    public static final String FIELD_DESCRIPTION = "description";

    @Size(max = 24)
    @NotNull
    @Column(name = "NAME", nullable = false, length = 24)
    private String name;

    @Size(max = 48)
    @Column(name = "DESCRIPTION", length = 48)
    private String description;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    public List<RoleAuthority> authorities;

}