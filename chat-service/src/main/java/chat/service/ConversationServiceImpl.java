package chat.service;

import chat.converter.MessagesConverter;
import chat.dao.ConversationDao;
import chat.dto.MessageDto;
import chat.entity.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    private ConversationDao dao;

    @Autowired
    private MessagesConverter converter;

    @Override
    public Conversation getConversationById(int id) {
        return dao.getConversationById(id);
    }

    @Override
    public Conversation getConversationByUID(int conversationUID) {
        return dao.getConversationByConversationUID(conversationUID);
    }

    @Override
    public void save(Conversation conversation) {
        dao.save(conversation);
    }

    @Override
    public List<MessageDto> chatHistory(int idClickUser, int currentUserId, int conversationUID) {
        Conversation conversation = getConversationByUID(conversationUID);
        Conversation conversationReverse = getConversationByUID(reversUID(conversationUID));
        if (conversation == null && conversationReverse == null) {
            Conversation newConversation = createNewConversation(idClickUser, currentUserId, conversationUID);
            return converter.convertToListDtoMessage(newConversation.getMessages());
        }
        return conversation == null ? converter.convertToListDtoMessage(conversationReverse.getMessages())
                : converter.convertToListDtoMessage(conversation.getMessages());
    }

    private int reversUID(int conversationUID) {
        StringBuilder result = new StringBuilder(String.valueOf(conversationUID));
        result.reverse();
        return Integer.parseInt(result.toString());
    }

    private Conversation createNewConversation(int idClickUser, int currentUserId, int UIDConversation) {
        Conversation conversation = new Conversation();
//        User userClick = userDao.findById(idClickUser);
//        User userCurrent = userDao.findById(currentUserId);
//        conversation.setUIDConversation(UIDConversation);
//        conversation.setUserDetailsSet(new HashSet<>(asList(userClick, userCurrent)));
        save(conversation);
        return conversation;
    }
}
