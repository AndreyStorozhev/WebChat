package auth.controllers;

import auth.entity.User;
import auth.service.UserService;
import auth.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Controller
public class UserController {
    @Autowired
    @Qualifier("userValidator")
    private Validator validator;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping({"/", "/home"})
    public String home() {
        return "index";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("user") User user, BindingResult result) {
        validator.validate(user, result);
        if (result.hasErrors())
            return "registration";
        userService.save(user);
        restTemplate.postForObject("http://chat-service/create/chat-user", user, User.class);
        userService.sendActivationMessage(user);
        securityService.autoLogin(user.getLogin(), user.getPassword());
        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String err, Model model, String logout) {
        if (err != null)
            model.addAttribute("error", "Login or password incorrect");
        if (logout != null)
            model.addAttribute("message", "Logged successful");
        return "login";
    }


    @RequestMapping("/activate/user/{id}")
    public String activate(@PathVariable("id") int id, Model model) {
        if (userService.activateAccount(id))
            model.addAttribute("message", "Аккаунт успешно активирован");
        else
            model.addAttribute("message", "Не удалось активировать аккаунт");
        return "login";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("loginInUsers", userService.loginInUsers());
        return "welcome";
    }

    @GetMapping("/chat/open")
    public String openChat(Model model) {
        String userLogin = userService.getUserLogin();
        User byLogin = userService.findByLogin(userLogin);
        model.addAttribute("username", userLogin);
        model.addAttribute("userId", byLogin.getId());
        List<User> userDetailsList = userService.loginInUsers();
        userDetailsList.remove(byLogin);
        model.addAttribute("allLoginUser", userDetailsList);
        return "chat";
    }
}
