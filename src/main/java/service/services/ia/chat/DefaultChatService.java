package service.services.ia.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.domain.dto.ia.PetChatMessageDTO;
import service.domain.dto.ia.PetChatRequest;
import service.domain.request.pet.PetChatResponse;
import service.services.ia.IAService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultChatService implements ChatService {

    private final IAService chatGptService;

    // Mensaje "system" para guiar el comportamiento del modelo.
    private static final String SYSTEM_PROMPT = """
            Eres un asistente conversacional experto en ayudar a las personas a encontrar su mascota ideal.
            Haz preguntas al usuario para conocer sus preferencias, necesidades y contexto.
            Cuando tengas suficiente información, recomienda el tipo de mascota perfecta para él (por ejemplo: perro pequeño activo, gato tranquilo, etc.).
            Mantén la conversación amigable, en español y no muestres información irrelevante ni salgas de tu rol.
            Si el usuario saluda o escribe cualquier cosa, guía siempre hacia la elección de mascota ideal.
            """;

    /**
     * Handles a conversational chat session for determining a user's ideal pet.
     * It appends the user's message to the ongoing chat history, limits the number of messages
     * sent to the AI to a predefined maximum, and integrates an AI response into the full conversation history.
     *
     * @param request the chat request containing the user's reply and existing messages in the conversation.
     * @return a {@code PetChatResponse} containing the AI's reply and the updated conversation history.
     */
    public PetChatResponse handleChat(PetChatRequest request) {
        final List<PetChatMessageDTO> fullMessages = Objects.nonNull(request.getMessages()) ? new ArrayList<>(request.getMessages()) : new ArrayList<>();
        fullMessages.add(new PetChatMessageDTO("user", request.getReply()));

        final int maxForAI = 15;
        final List<PetChatMessageDTO> limitedMessages;

        if (fullMessages.size() > maxForAI) {
            limitedMessages = fullMessages.subList(fullMessages.size() - maxForAI, fullMessages.size());
        } else {
            limitedMessages = new ArrayList<>(fullMessages);
        }

        final List<PetChatMessageDTO> messagesForIA = new ArrayList<>();
        messagesForIA.add(new PetChatMessageDTO("system", SYSTEM_PROMPT));
        messagesForIA.addAll(limitedMessages);

        final String aiReply = this.chatGptService.sendChat(messagesForIA);

        final List<PetChatMessageDTO> updatedMessages = new ArrayList<>(fullMessages);
        updatedMessages.add(new PetChatMessageDTO("assistant", aiReply));

        PetChatResponse response = new PetChatResponse();
        response.setReply("");
        response.setMessages(updatedMessages);
        return response;
    }


}
