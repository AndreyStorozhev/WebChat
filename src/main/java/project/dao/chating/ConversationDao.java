package project.dao.chating;

import project.entity.Conversation;

public interface ConversationDao {
    Conversation getConversationById(int id);
    Conversation getConversationByUIDConversation(int UIDConversation);
    void save(Conversation conversation);
}
