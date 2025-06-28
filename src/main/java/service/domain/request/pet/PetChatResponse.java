package service.domain.request.pet;

import lombok.Data;
import service.domain.dto.ia.PetChatMessageDTO;

import java.util.List;

@Data
public class PetChatResponse {

    private String reply;
    private List<PetChatMessageDTO> messages;

}
