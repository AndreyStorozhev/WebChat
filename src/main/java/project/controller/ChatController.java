package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import project.dto.MessageDto;
import project.entity.Message;
import project.entity.UserDetails;
import project.service.user.UserDetailsService;
import project.service.chating.ConversationService;
import project.service.chating.MessageService;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private UserDetailsService service;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    @RequestMapping("/chat")
    public String some(Model model) {
        String userLogin = service.getUserLogin();
        UserDetails byLogin = service.findByLogin(userLogin);
        model.addAttribute("username", userLogin);
        model.addAttribute("userId", byLogin.getId());
        List<UserDetails> userDetailsList = service.loginInUsers();
        userDetailsList.remove(byLogin);
        model.addAttribute("allLoginUser", userDetailsList);
        return "chat";
    }

    @MessageMapping("/hello")
    public void chatting(MessageDto message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        message.setFormatDate(dateFormat.format(new Date()));
        Message saveMessage = messageService.saveMessage(message);
        messagingTemplate.convertAndSendToUser("AndreBog", "/topic/chat/" + saveMessage.getConversation().getUIDConversation(), message);
        messagingTemplate.convertAndSend("/topic/chat/" + saveMessage.getConversation().getUIDConversation(), message);
    }

    @RequestMapping("/chat/check")
    @ResponseBody
    public List<MessageDto> checkChat(@RequestParam(value = "idClickUser") int idClickUser,
                                  @RequestParam(value = "currentUserId") int currentUserId,
                                  @RequestParam(value = "UIDConversation") int UIDConversation) {
        return conversationService.chatHistory(idClickUser, currentUserId, UIDConversation);
    }
}
