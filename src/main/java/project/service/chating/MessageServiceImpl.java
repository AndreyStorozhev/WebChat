package project.service.chating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.converter.MessagesConverter;
import project.dao.chating.MessageDao;
import project.dto.MessageDto;
import project.entity.Message;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MessagesConverter converter;

    @Override
    public Message saveMessage(MessageDto message) {
        Message saveMessage = converter.convertToMessageFromDto(message);
        messageDao.saveMessage(saveMessage);
        return saveMessage;
    }
}
