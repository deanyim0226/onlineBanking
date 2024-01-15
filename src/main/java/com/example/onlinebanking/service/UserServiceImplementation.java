package com.example.onlinebanking.service;

import com.example.onlinebanking.dao.UserRepository;
import com.example.onlinebanking.domain.Role;
import com.example.onlinebanking.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;


    @Override
    public User save(User user) {
        String encryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public Boolean isAdmin(User user) {

        List<Role> retrievedRole = user.getRoles();

        for(Role role : retrievedRole){

            if(role.getName().equals("Admin")){
                return true;
            }
        }
        return false;
    }


}
