package service.domain.dto.foundation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.entity.foundation.FoundationPublicationImage;

import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoundationPublicationImageDTO extends BaseDTO {

    private String imageUrl;

    public FoundationPublicationImageDTO(FoundationPublicationImage image) {
        super(image);
        if (Objects.nonNull(image)) {
            this.imageUrl = image.getImageUrl();
        }
    }

}