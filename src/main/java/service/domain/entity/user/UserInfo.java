package service.domain.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import service.domain.entity.BaseEntity;
import service.domain.types.Gender;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo extends BaseEntity {

    public static final String FIELD_FIRSTNAME = "firstname";
    public static final String FIELD_LASTNAME = "lastname";

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "GENDER")
    @Enumerated(EnumType  .STRING)
    private Gender gender;

    @Column(name = "BIRTH_DATE")
    private LocalDate birth;

    @Size(max = 64)
    @NotNull
    @Column(name = "COUNTRY", nullable = false, length = 64)
    private String country;

    @Size(max = 64)
    @NotNull
    @Column(name = "CITY", nullable = false, length = 64)
    private String city;

    public String getName() {
        return String.format("%s %s", firstname.trim(), lastname.trim());
    }

}
