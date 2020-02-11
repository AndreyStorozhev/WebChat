package chat.dao;

import chat.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserDao extends JpaRepository<ChatUser, Integer> {
}
