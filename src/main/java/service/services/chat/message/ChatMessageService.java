package service.services.chat.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import service.domain.entity.chat.ChatMessage;
import service.domain.entity.user.User;
import service.domain.request.chat.ChatMessageRequest;
import service.services.BaseService;

public interface ChatMessageService extends BaseService<ChatMessage> {

    Page<ChatMessage> findByThreadId(Long threadId, Pageable pageable);

    ChatMessage create(ChatMessageRequest request, User user);

}
