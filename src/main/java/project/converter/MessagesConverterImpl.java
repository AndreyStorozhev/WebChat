package project.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.dto.MessageDto;
import project.entity.Conversation;
import project.entity.Message;
import project.service.chating.ConversationService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Component
public class MessagesConverterImpl implements MessagesConverter {
    @Autowired
    private ConversationService conversationService;

    @Override
    public List<MessageDto> convertToListDtoMessage(Set<Message> messageSet) {
        List<Message> list = new ArrayList<>(messageSet);
        list.sort(Comparator.comparingInt(Message::getId));
        List<MessageDto> resultList = new ArrayList<>();
        for (Message message : list) {
            MessageDto messageDto = new MessageDto();
            messageDto.setMsg(message.getMsg());
            messageDto.setName(message.getName());
            messageDto.setFormatDate(message.getFormatDate());
            resultList.add(messageDto);
        }
        return resultList;
    }

    @Override
    public Message convertToMessageFromDto(MessageDto message) {
        Conversation conversation = conversationService.getConversationByUIDConversation(Integer.parseInt(message.getConversationUID()));
        if (conversation == null)
            conversation = conversationService.getConversationByUIDConversation(Integer.parseInt(new StringBuilder(message.getConversationUID()).reverse().toString()));

        Message saveMessage = new Message();

        saveMessage.setName(message.getName());
        saveMessage.setMsg(message.getMsg());
        saveMessage.setFormatDate(message.getFormatDate());
        saveMessage.setConversation(conversation);

        return saveMessage;
    }
}
