package br.com.ero.demoparkapi.jwt;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User     {
    private br.com.ero.demoparkapi.config.entity.User user;
    public JwtUserDetails(br.com.ero.demoparkapi.config.entity.User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public JwtUserDetails getId(){
        return this.user.getId();
    }
    public String getRole(){
        return this.user.getRole().name();
    }

}
