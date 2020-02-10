package chat.service;

import chat.converter.MessagesConverter;
import chat.dao.MessageDao;
import chat.dto.MessageDto;
import chat.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MessagesConverter converter;

    @Override
    public Message saveMessage(MessageDto message) {
        Message saveMessage = converter.convertToMessageFromDto(message);
        messageDao.save(saveMessage);
        return saveMessage;
    }
}
