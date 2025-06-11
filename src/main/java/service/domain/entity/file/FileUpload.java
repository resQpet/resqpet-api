package service.domain.entity.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import service.domain.entity.BaseEntity;
import service.domain.entity.user.User;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FILE_UPLOAD")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUpload extends BaseEntity {

    public static final String FIELD_ORGANIZATION = "organization";
    public static final String FIELD_UPLOADED_BY = "uploadedBy";

    @Column(name = "URI")
    private String uri;

    @Column(name = "CHECKSUM", nullable = false)
    private String checksum;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "UPLOADED_BY_ID", nullable = false)
    private User uploadedBy;

}
