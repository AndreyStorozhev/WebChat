package project.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.dao.user.UserDetailsDao;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsServiceSecurityImpl")
public class UserDetailsServiceSecurityImpl implements UserDetailsService {

    private final UserDetailsDao dao;

    @Autowired
    public UserDetailsServiceSecurityImpl(UserDetailsDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        project.entity.UserDetails userDetails = dao.findByLogin(login);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userDetails.getRole()));
        return new User(userDetails.getLogin(), userDetails.getPassword(), grantedAuthorities);
    }
}
