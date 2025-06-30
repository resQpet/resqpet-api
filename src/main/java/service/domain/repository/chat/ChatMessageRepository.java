package service.domain.repository.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import service.domain.entity.chat.ChatMessage;
import service.domain.entity.chat.ChatThread;
import service.domain.repository.BaseRepository;

public interface ChatMessageRepository extends BaseRepository<ChatMessage> {

    Page<ChatMessage> findByChatThread(ChatThread chatThread, Pageable pageable);

}
