package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import project.entity.UserDetails;
import project.model.Message;
import project.service.UserDetailsService;

import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class ChatController {

    @Autowired
    private UserDetailsService service;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

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
    @SendTo("/topic/chat/")
    public Message chatting(Message message) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm | dd.MM");
        return new Message(message.getName(), message.getMsg(), dateFormat.format(date));
    }

    @RequestMapping("/chat/check")
    @ResponseBody
    public String checkChat(@RequestParam(value = "UIDConversation") String UIDConversation) {

        return UIDConversation;
    }
}
