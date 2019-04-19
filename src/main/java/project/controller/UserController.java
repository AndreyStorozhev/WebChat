package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import project.entity.UserDetails;
import project.service.user.UserDetailsService;
import project.service.security.SecurityService;


@Controller
public class UserController {

    @Autowired
    private Validator validator;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping({"/", "/home"})
    public String home() {
        return "index";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserDetails());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("user") UserDetails userDetails, BindingResult result, Model model) {
        validator.validate(userDetails, result);

        if (result.hasErrors()) {
            return "registration";
        }
        userDetailsService.save(userDetails);
        userDetailsService.sendActivationMessage(userDetails);
        securityService.autoLogin(userDetails.getLogin(), userDetails.getPassword());

        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String err, Model model, String logout) {
        if (err != null) {
            model.addAttribute("error", "login or password incorrect");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged successful");
        }
        return "login";
    }


    @RequestMapping("/activate/user/{id}")
    public String activate(@PathVariable("id") int id, Model model) {
        if (userDetailsService.activateAccount(id))
            model.addAttribute("message", "Аккаунт успешно активирован");
        else
            model.addAttribute("message", "Не удалось активировать аккаунт");

        return "login";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("loginInUsers", userDetailsService.loginInUsers());
        return "welcome";
    }
}
