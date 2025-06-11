package service.domain.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import service.domain.entity.BaseEntity;
import service.domain.entity.auth.Role;
import service.domain.types.UserStatus;

import java.util.Optional;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseEntity {

    public static final String FIELD_INFO = "info";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_CONTACT = "contact";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_DOCUMENT = "document";
    public static final String FIELD_ORGANIZATION = "organization";
    public static final String FIELD_ZONES = "zones";

    protected User(User user) {
        super(user.getId(), user.getCreatedAt(), user.getUpdatedAt());
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.document = user.getDocument();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.role = user.getRole();
        this.info = user.getInfo();
    }

    @Column(name = "USERNAME", unique = true, length = 45)
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DOCUMENT")
    private String document;

    @JsonIgnore
    @ToString.Exclude
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "STATUS")
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserInfo info;


    @JsonIgnore
    public String getName() {
        return Optional.ofNullable(info).map(UserInfo::getName).orElse(null);
    }

}
