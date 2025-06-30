package service.domain.entity.foundation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.hibernate.annotations.ColumnDefault;
import service.domain.entity.BaseEntity;
import service.domain.types.FoundationStatus;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "foundation")
public class Foundation extends BaseEntity {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_STATUS = "status";

    @Size(max = 100)
    @NotNull
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "FOUNDED_DATE")
    private LocalDate foundedDate;

    @Size(max = 64)
    @Column(name = "EMAIL", length = 64)
    private String email;

    @Size(max = 20)
    @Column(name = "PHONE", length = 20)
    private String phone;

    @Size(max = 255)
    @Column(name = "WEBSITE")
    private String website;

    @ColumnDefault("1")
    @Column(name = "MEMBER_COUNT")
    private Integer memberCount;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private FoundationStatus status;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "foundation")
    private List<FoundationLocation> locations;

}