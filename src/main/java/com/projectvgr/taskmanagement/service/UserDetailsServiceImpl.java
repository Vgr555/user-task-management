package com.projectvgr.taskmanagement.service;

import com.projectvgr.taskmanagement.entities.UserEntity;
import com.projectvgr.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Load");
        UserEntity user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username " + username + " not found")
        );
        return UseDetailsImpl.build(user);
    }
}
