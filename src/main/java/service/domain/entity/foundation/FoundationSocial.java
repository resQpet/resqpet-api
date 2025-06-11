package service.domain.entity.foundation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "foundation_social", schema = "resqpet")
public class FoundationSocial extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FOUNDATION_ID", nullable = false)
    private Foundation foundation;

    @Size(max = 255)
    @Column(name = "FACEBOOK_URL")
    private String facebookUrl;

    @Size(max = 255)
    @Column(name = "INSTAGRAM_URL")
    private String instagramUrl;

    @Size(max = 255)
    @Column(name = "DISCORD_URL")
    private String discordUrl;

    @Size(max = 255)
    @Column(name = "YOUTUBE_URL")
    private String youtubeUrl;

}