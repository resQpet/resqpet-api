package service.services.chat.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.chat.ChatMessage;
import service.domain.entity.chat.ChatThread;
import service.domain.entity.user.User;
import service.domain.repository.chat.ChatMessageRepository;
import service.domain.request.chat.ChatMessageRequest;
import service.services.DefaultBaseService;
import service.services.chat.ChatThreadService;

@Getter
@Service
@AllArgsConstructor
public class DefaultChatMessageService extends DefaultBaseService<ChatMessage> implements ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatThreadService threadService;

    /**
     * Finds and retrieves a paginated list of chat messages for a given chat thread ID.
     *
     * @param threadId the ID of the chat thread whose messages need to be retrieved
     * @param pageable the pagination and sorting information
     * @return a {@code Page} containing the {@code ChatMessage} entities belonging to the specified chat thread
     */
    @Override
    public Page<ChatMessage> findByThreadId(Long threadId, Pageable pageable) {
        final ChatThread chat = this.threadService.findById(threadId);
        return this.repository.findByChatThread(chat, pageable);
    }

    /**
     * Creates a new chat message in the given chat thread.
     *
     * @param request the request object containing the details of the chat message to create, including chat thread ID
     *                and the message content
     * @param user    the user who is sending the chat message
     * @return the created {@code ChatMessage} object
     */
    @Override
    @Transactional
    public ChatMessage create(ChatMessageRequest request, User user) {
        final ChatThread thread = this.threadService.findById(request.getChatThreadId());
        final ChatMessage message = ChatMessage.builder()
                .senderUser(user)
                .chatThread(thread)
                .message(request.getMessage())
                .build();
        return this.create(message);
    }
}
