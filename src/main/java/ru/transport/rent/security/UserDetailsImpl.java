package ru.transport.rent.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import ru.transport.rent.entity.User;

import lombok.Getter;


/**
 * Реализация UserDetails.
 */
@Getter
public class UserDetailsImpl implements UserDetails {

    private final User user;


    public UserDetailsImpl(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole()
                .getName();
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public String getUsername() {
        return user.getUserName();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
