package project.service.chating;

import project.dto.MessageDto;
import project.entity.Conversation;

import java.util.List;

public interface ConversationService {
    Conversation getConversationById(int id);
    Conversation getConversationByUID(int UIDConversation);
    void save(Conversation conversation);
    List<MessageDto> chatHistory(int idClickUser, int currentUserId, int UIDConversation);
}
