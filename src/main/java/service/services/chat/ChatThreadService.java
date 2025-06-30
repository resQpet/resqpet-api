package service.services.chat;

import service.domain.entity.chat.ChatThread;
import service.domain.entity.user.User;
import service.domain.request.chat.ChatThreadRequest;
import service.services.BaseService;

public interface ChatThreadService extends BaseService<ChatThread> {

    ChatThread create(User user, ChatThreadRequest request);

}
