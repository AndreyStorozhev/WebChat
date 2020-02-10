package auth.dao;

import auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByLogin(String login);
    User findById(int id);
}
