package service.domain.entity.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import service.domain.entity.BaseEntity;
import service.domain.entity.foundation.Foundation;
import service.domain.entity.user.User;
import service.domain.types.chat.ChatStatus;

import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "chat_thread", schema = "RESQPET")
public class ChatThread extends BaseEntity {

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FOUNDATION_ID", nullable = false)
    private Foundation foundation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Size(max = 128)
    @Column(name = "SUBJECT", length = 128)
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ChatStatus status;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "chatThread")
    private List<ChatMessage> message;

}