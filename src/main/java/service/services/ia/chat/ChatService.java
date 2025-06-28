package service.services.ia.chat;

import service.domain.dto.ia.PetChatRequest;
import service.domain.request.pet.PetChatResponse;

public interface ChatService {

    PetChatResponse handleChat(PetChatRequest request);

}
