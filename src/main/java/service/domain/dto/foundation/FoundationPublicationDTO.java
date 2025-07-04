package service.domain.dto.foundation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import service.domain.dto.BaseDTO;
import service.domain.entity.foundation.FoundationPublication;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoundationPublicationDTO extends BaseDTO {

    private BaseFoundationDTO foundation;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDate eventDate;
    private List<FoundationPublicationImageDTO> images;

    public FoundationPublicationDTO(FoundationPublication publication) {
        super(publication);
        if (Objects.nonNull(publication)) {
            this.foundation = new BaseFoundationDTO(publication.getFoundation());
            this.title = publication.getTitle();
            this.content = publication.getContent();
            this.eventDate = publication.getEventDate();
            this.images = publication.getImages().stream().map(FoundationPublicationImageDTO::new).toList();
        }
    }

}