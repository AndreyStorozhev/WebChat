package chat.dao;

import chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDao extends JpaRepository<Message, Integer> {
}
