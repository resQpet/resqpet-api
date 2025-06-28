package service.services.ia;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.config.http.ia.IAApiProperties;
import service.domain.dto.ia.PetChatMessageDTO;
import service.domain.models.ia.Message;
import service.domain.request.ia.ChatGPTResponse;
import service.domain.request.ia.IARequest;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultIAService implements IAService {

    private final RestTemplate restTemplate;
    private final IAApiProperties properties;

    public String sendChat(List<PetChatMessageDTO> messages) {
        // Map our DTOs to OpenAI format
        final List<Message> aiMessages = messages.stream().map(m -> new Message(m.getRole(), m.getContent())).toList();
        final IARequest requestBody = new IARequest("meta-llama/llama-3-8b-instruct", aiMessages, 100);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(properties.getKey());

        final HttpEntity<IARequest> entity = new HttpEntity<>(requestBody, headers);
        final ResponseEntity<ChatGPTResponse> response = restTemplate.exchange(properties.getUrl(), HttpMethod.POST, entity, ChatGPTResponse.class);

        if (Objects.nonNull(response.getBody()) && Objects.nonNull(response.getBody().getChoices()) && !response.getBody().getChoices().isEmpty()) {
            return response.getBody().getChoices().getFirst().getMessage().getContent().trim();
        }
        return "No response from assistant.";
    }
}
