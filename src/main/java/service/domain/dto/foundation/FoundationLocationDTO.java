
package service.domain.dto.foundation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.entity.foundation.FoundationLocation;

import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoundationLocationDTO extends BaseDTO {

    private String country;
    private String city;
    private String address;
    private String postalCode;

    public FoundationLocationDTO(FoundationLocation location) {
        super(location);
        if (Objects.nonNull(location)) {
            this.country = location.getCountry();
            this.city = location.getCity();
            this.address = location.getAddress();
            this.postalCode = location.getPostalCode();
        }
    }

}