package chat.service;

import chat.dto.MessageDto;
import chat.entity.Conversation;

import java.util.List;

public interface ConversationService {
    Conversation getConversationById(int id);
    Conversation getConversationByUID(int conversationUID);
    void save(Conversation conversation);
    List<MessageDto> chatHistory(int idClickUser, int currentUserId, int conversationUID);
}
