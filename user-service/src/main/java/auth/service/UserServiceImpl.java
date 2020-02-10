package auth.service;

import auth.dao.UserRepo;
import auth.entity.User;
import auth.service.mail.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo repo;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        repo.save(user);
    }

    @Override
    public User findById(int id) {
        return repo.findById(id);
    }

    @Override
    public User findByLogin(String login) {
        return repo.findByLogin(login);
    }

    @Override
    public boolean activateAccount(int id) {
        User user = repo.findById(id);
        if (user.isActivateAccount())
            return false;
        user.setActivateAccount(true);
        repo.save(user);
        return true;
    }

    @Override
    public List<User> loginInUsers() {
        List<String> namesLoginInUsers = getNamesLoginInUsers();
        List<User> loginInUsers = new ArrayList<>();
        for (String name : namesLoginInUsers)
            loginInUsers.add(repo.findByLogin(name));
        return loginInUsers;
    }

    @Override
    public void sendActivationMessage(User user) {
        String msg = String.format("Hello, %s \n Welcome to MyChat. \n Please visit nest link: http://localhost:8070/activate/user/%s",
                user.getLogin(), user.getId());
        mailSenderService.sendMessage(user.getEmail(), "Activate account", msg);
    }

    @Override
    public String getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private List<String> getNamesLoginInUsers() {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        List<String> namesLoginInUsers = new ArrayList<>();
        for (Object principal : principals) {
            if (principal instanceof org.springframework.security.core.userdetails.User)
                namesLoginInUsers.add(((org.springframework.security.core.userdetails.User) principal).getUsername());
        }
        return namesLoginInUsers;
    }
}
