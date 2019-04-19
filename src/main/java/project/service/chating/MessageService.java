package project.service.chating;

import project.dto.MessageDto;
import project.entity.Message;

public interface MessageService {
    Message saveMessage(MessageDto message);
}
