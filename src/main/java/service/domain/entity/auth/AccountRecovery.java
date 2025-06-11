package service.domain.entity.auth;

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

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_ACCOUNT_RECOVERY")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRecovery extends BaseEntity {

    @Column(name = "TOKEN", nullable = false, unique = true)
    private String token;

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "USER_AGENT", nullable = false)
    private String agent;

    @Column(name = "REMOTE_ADDRESS", nullable = false)
    private String remoteIp;

    @Column(name = "CONSUMED", nullable = false)
    private boolean consumed;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

}
