package project.service.chating;

import project.entity.Conversation;

public interface ConversationService {
    Conversation getConversationById(int id);
    Conversation getConversationByUIDConversation(int UIDConversation);
    Conversation createNewConversation(int idClickUser, int currentUserId, int UIDConversation);
    void save(Conversation conversation);
}
