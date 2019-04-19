package project.service.chating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.chating.MessageDao;
import project.dto.MessageDto;
import project.entity.Conversation;
import project.entity.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private ConversationService conversationService;

    @Override
    public Message saveMessage(MessageDto message) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Conversation conversation = conversationService.getConversationByUIDConversation(Integer.parseInt(message.getConversationUID()));

        Message saveMessage = new Message();

        saveMessage.setName(message.getName());
        saveMessage.setMsg(message.getMsg());
        saveMessage.setFormatDate(dateFormat.format(date));
        saveMessage.setConversation(conversation);
        messageDao.saveMessage(saveMessage);

        return saveMessage;
    }
}
