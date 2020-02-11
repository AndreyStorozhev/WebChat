package chat.service;

import chat.dao.ChatUserDao;
import chat.entity.ChatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatUserServiceImpl implements ChatUserService {
    @Autowired
    private ChatUserDao dao;

    @Override
    public void save(ChatUser chatUser) {
        dao.save(chatUser);
    }
}
