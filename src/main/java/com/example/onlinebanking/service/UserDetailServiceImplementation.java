package com.example.onlinebanking.service;

import com.example.onlinebanking.domain.Role;
import com.example.onlinebanking.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username: " + username);

        User user = userService.findByUsername(username);
        Collection<GrantedAuthority> authorities = new HashSet<>();

        if(user != null){

            for(Role role: user.getRoles()){
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(),encoder.encode(user.getPassword()),authorities);
        }

        return null;

    }
}
