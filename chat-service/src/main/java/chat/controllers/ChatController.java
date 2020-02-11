package chat.controllers;

import chat.dao.ChatUserDao;
import chat.dto.MessageDto;
import chat.entity.ChatUser;
import chat.entity.Message;
import chat.service.ChatUserService;
import chat.service.ConversationService;
import chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatUserService service;

    @PostMapping("/create/chat-user")
    public void createChatUser(@RequestBody ChatUser user) {
        service.save(user);
        System.out.println(user);
    }

    @MessageMapping("/hello")
    public void chatting(MessageDto message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        message.setFormatDate(dateFormat.format(new Date()));
        Message saveMessage = messageService.saveMessage(message);
//        messagingTemplate.convertAndSendToUser("AndreBog", "/topic/chat/" + saveMessage.getConversation().getUIDConversation(), message);
        messagingTemplate.convertAndSend("/topic/chat/" + saveMessage.getConversation().getConversationUID(), message);
    }

    @RequestMapping("/chat/check")
    @ResponseBody
    public List<MessageDto> checkChat(@RequestParam(value = "idClickUser") int idClickUser,
                                      @RequestParam(value = "currentUserId") int currentUserId,
                                      @RequestParam(value = "UIDConversation") int conversationUID) {
        return conversationService.chatHistory(idClickUser, currentUserId, conversationUID);
    }
}
