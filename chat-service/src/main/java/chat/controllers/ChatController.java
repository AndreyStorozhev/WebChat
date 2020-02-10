package chat.controllers;

import chat.dto.MessageDto;
import chat.entity.Message;
import chat.service.ConversationService;
import chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/chat")
    public String some(Model model) {
        String userLogin = service.getUserLogin();
        User byLogin = service.findByLogin(userLogin);
        model.addAttribute("username", userLogin);
        model.addAttribute("userId", byLogin.getId());
        List<User> userDetailsList = service.loginInUsers();
        userDetailsList.remove(byLogin);
        model.addAttribute("allLoginUser", userDetailsList);
        return "chat";
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
