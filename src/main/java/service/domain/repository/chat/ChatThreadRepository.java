package service.domain.repository.chat;

import service.domain.entity.chat.ChatThread;
import service.domain.entity.foundation.Foundation;
import service.domain.entity.user.User;
import service.domain.repository.BaseRepository;
import service.domain.types.chat.ChatStatus;

import java.util.Optional;

public interface ChatThreadRepository extends BaseRepository<ChatThread> {

    Optional<ChatThread> findByUserAndFoundationAndStatus(User user, Foundation foundation, ChatStatus status);

}
