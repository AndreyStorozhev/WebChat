package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import project.dto.MessageDto;
import project.entity.Conversation;
import project.entity.Message;
import project.entity.UserDetails;
import project.service.user.UserDetailsService;
import project.service.chating.ConversationService;
import project.service.chating.MessageService;

import java.text.SimpleDateFormat;
import java.util.*;


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

    @RequestMapping("/some")
    public String some(Model model) {
        String userLogin = service.getUserLogin();
        UserDetails byLogin = service.findByLogin(userLogin);
        model.addAttribute("username", userLogin);
        model.addAttribute("userId", byLogin.getId());
        model.addAttribute("allLoginUser", service.loginInUsers());
        return "some";
    }

    @RequestMapping("/chat/all")
    public String all() {
        return "chat";
    }

    @MessageMapping("/hello")
    public void chatting(MessageDto message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        messageService.saveMessage(message);
        message.setFormatDate(dateFormat.format(new Date()));
        messagingTemplate.convertAndSend("/topic/chat", message);
    }

    @RequestMapping("/chat/check")
    @ResponseBody
    public int checkChat(@RequestParam(value = "idClickUser") int idClickUser,
                                  @RequestParam(value = "currentUserId") int currentUserId,
                                  @RequestParam(value = "UIDConversation") int UIDConversation) {
        Conversation conversation = conversationService.getConversationByUIDConversation(UIDConversation);
        ModelAndView model = new ModelAndView("some");
        if (conversation != null) {
            model.addObject("messageConversation", conversation.getMessages());
            return UIDConversation;
        }
        Conversation newConversation = conversationService.createNewConversation(idClickUser, currentUserId, UIDConversation);
        model.addObject("messageConversation", newConversation.getMessages());
        return UIDConversation;
    }

    private List<MessageDto> convertToListDtoMessage(Set<Message> messageSet) {
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
}
