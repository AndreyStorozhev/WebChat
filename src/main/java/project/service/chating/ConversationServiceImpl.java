package project.service.chating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.user.UserDetailsDao;
import project.dao.chating.ConversationDao;
import project.entity.Conversation;
import project.entity.UserDetails;

import static java.util.Arrays.asList;
import java.util.HashSet;

@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    private ConversationDao dao;

    @Autowired
    private UserDetailsDao userDao;

    @Override
    public Conversation getConversationById(int id) {
        return dao.getConversationById(id);
    }

    @Override
    public Conversation getConversationByUIDConversation(int UIDConversation) {
        return dao.getConversationByUIDConversation(UIDConversation);
    }

    @Override
    public Conversation createNewConversation(int idClickUser, int currentUserId, int UIDConversation) {
        Conversation conversation = new Conversation();
        UserDetails userClick = userDao.findById(idClickUser);
        UserDetails userCurrent = userDao.findById(currentUserId);
        conversation.setUIDConversation(UIDConversation);
        conversation.setUserDetailsSet(new HashSet<>(asList(userClick, userCurrent)));
        save(conversation);
        return conversation;
    }

    @Override
    public void save(Conversation conversation) {
        dao.save(conversation);
    }
}
