package auth.service;

import auth.entity.User;
import java.util.List;

public interface UserService {
    void save(User user);
    User findById(int id);
    User findByLogin(String login);
    boolean activateAccount(int id);
    List<User> loginInUsers();
    void sendActivationMessage(User user);
    String getUserLogin();
}
