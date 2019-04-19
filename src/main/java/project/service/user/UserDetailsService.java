package project.service.user;

import project.entity.UserDetails;

import java.util.List;

public interface UserDetailsService {
    void save(UserDetails userDetails);
    UserDetails findById(int id);
    UserDetails findByLogin(String login);
    boolean activateAccount(int id);
    List<UserDetails> loginInUsers();
    void sendActivationMessage(UserDetails userDetails);
    String getUserLogin();
}
