package service.domain.request.ia;

import lombok.Data;
import service.domain.models.ia.Choice;

import java.util.List;

@Data
public class ChatGPTResponse {

    private List<Choice> choices;

}
