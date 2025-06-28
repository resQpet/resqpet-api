package service.domain.request.ia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import service.domain.models.ia.Message;

import java.util.List;

@Data
@AllArgsConstructor
public class IARequest {

    private String model;
    private List<Message> messages;

    @JsonProperty("max_tokens")
    private int maxTokens;

}
