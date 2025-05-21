package com.richardcodez.spring_security_demo.service;

import com.richardcodez.spring_security_demo.dao.UserRepo;
import com.richardcodez.spring_security_demo.model.User;
import com.richardcodez.spring_security_demo.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }

        return new UserDetailsImpl(user);
    }
}
