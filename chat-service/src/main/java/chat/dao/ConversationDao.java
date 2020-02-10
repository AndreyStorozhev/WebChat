package chat.dao;

import chat.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationDao extends JpaRepository<Conversation, Integer> {
    Conversation getConversationById(int id);
    Conversation getConversationByConversationUID(int conversationUID);
}
