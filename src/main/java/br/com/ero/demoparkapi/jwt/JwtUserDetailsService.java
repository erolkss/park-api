package br.com.ero.demoparkapi.jwt;

import br.com.ero.demoparkapi.config.entity.User;
import br.com.ero.demoparkapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserService userService;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserName(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String username){
        User.Role role = userService.getRoleByUserName(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}
