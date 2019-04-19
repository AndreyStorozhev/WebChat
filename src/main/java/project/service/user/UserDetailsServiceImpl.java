package project.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.dao.user.UserDetailsDao;
import project.entity.UserDetails;
import project.service.email.MailSender;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserDetailsDao dao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void save(UserDetails userDetails) {
        userDetails.setPassword(encoder.encode(userDetails.getPassword()));
        userDetails.setRole("ROLE_USER");
        dao.save(userDetails);
    }

    @Override
    public UserDetails findById(int id) {
        return dao.findById(id);
    }

    @Override
    public UserDetails findByLogin(String login) {
        return dao.findByLogin(login);
    }

    @Override
    public boolean activateAccount(int id) {
        UserDetails userDetails = dao.findById(id);

        if (userDetails.isActivateAccount())
            return false;

        userDetails.setActivateAccount(true);
        dao.update(userDetails);

        return true;
    }

    @Override
    public List<UserDetails> loginInUsers() {
        List<String> namesLoginInUsers = getNamesLoginInUsers();
        List<UserDetails> loginInUsers = new ArrayList<>();
        for (String name : namesLoginInUsers) {
            loginInUsers.add(dao.findByLogin(name));
        }
        return loginInUsers;
    }

    @Override
    public void sendActivationMessage(UserDetails userDetails) {
        String msg = String.format("Hello, %s \n Welcome to MyChat. \n Please visit nest link: http://localhost:8070/activate/user/%s",
                userDetails.getLogin(), userDetails.getId());
        mailSender.sendMessage(userDetails.getEmail(), "Activate account", msg);
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
            if (principal instanceof User)
                namesLoginInUsers.add(((User) principal).getUsername());
        }
        return namesLoginInUsers;
    }
}
