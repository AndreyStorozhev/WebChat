package project.dao.user;

import project.entity.UserDetails;

public interface UserDetailsDao {
    void save(UserDetails userDetails);
    UserDetails findById(int id);
    UserDetails findByLogin(String login);
    void update(UserDetails userDetails);
}
