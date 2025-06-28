package service.services.ia;

import service.domain.dto.ia.PetChatMessageDTO;

import java.util.List;

public interface IAService {

    String sendChat(List<PetChatMessageDTO> messages);

}
