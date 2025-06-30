package service.services.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.chat.ChatThread;
import service.domain.entity.foundation.Foundation;
import service.domain.entity.user.User;
import service.domain.repository.chat.ChatThreadRepository;
import service.domain.request.chat.ChatThreadRequest;
import service.domain.types.chat.ChatStatus;
import service.services.DefaultBaseService;
import service.services.foundation.FoundationService;

import java.util.Optional;

@Getter
@Service
@AllArgsConstructor
public class DefaultChatThreadService extends DefaultBaseService<ChatThread> implements ChatThreadService {

    private final ChatThreadRepository repository;
    private final FoundationService foundationService;

    /**
     * Creates a new chat thread or retrieves an existing one if a thread with the same user, foundation,
     * and open status already exists.
     *
     * @param user    the user initiating the chat thread
     * @param request the request containing information for creating the chat thread, such as foundation ID and subject
     * @return the newly created chat thread or the existing one if already present
     */
    @Override
    @Transactional
    public ChatThread create(User user, ChatThreadRequest request) {
        final Foundation foundation = this.foundationService.findById(request.getFoundationId());
        final Optional<ChatThread> existing = this.repository.findByUserAndFoundationAndStatus(user, foundation, ChatStatus.OPEN);

        if (existing.isPresent()) {
            return existing.get();
        }

        final ChatThread chatThread = ChatThread.builder()
                .user(user)
                .foundation(foundation)
                .subject(request.getSubject())
                .status(ChatStatus.OPEN)
                .build();
        return this.create(chatThread);
    }

}
