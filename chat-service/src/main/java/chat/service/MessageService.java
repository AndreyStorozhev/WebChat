package chat.service;

import chat.dto.MessageDto;
import chat.entity.Message;

public interface MessageService {
    Message saveMessage(MessageDto message);
}
