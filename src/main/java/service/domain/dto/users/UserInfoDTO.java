package service.domain.dto.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.entity.user.UserInfo;
import service.domain.types.Gender;

import java.time.LocalDate;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDTO extends BaseDTO {

    private String firstname;
    private String lastname;
    private String image;
    private Gender gender;
    private LocalDate birth;
    private String country;
    private String city;

    public String getName() {
        return String.format("%s %s", firstname.trim(), lastname.trim());
    }

    public UserInfoDTO(UserInfo info) {
        super(info);
        if (Objects.nonNull(info)) {
            this.firstname = info.getFirstname();
            this.lastname = info.getLastname();
            this.image = info.getImage();
            this.gender = info.getGender();
            this.birth = info.getBirth();
            this.country = info.getCountry();
            this.city = info.getCity();
        }
    }
}
