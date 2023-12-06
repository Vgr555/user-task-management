package com.projectvgr.taskmanagement.service;

import com.projectvgr.taskmanagement.entities.UserEntity;
import com.projectvgr.taskmanagement.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UseDetailsImpl implements UserDetails {
    //UserDetails obj1 = new UserDetailsImpl()

    private Integer id;
    private String name;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UseDetailsImpl(Integer id, String name, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetails build(UserEntity user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = new ArrayList<>(List.of(Role.values()));
        for(Role role : roles){
//            GrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }

        return new UseDetailsImpl(user.getId(), user.getName(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
