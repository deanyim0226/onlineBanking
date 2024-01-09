package com.example.onlinebanking.service;

import com.example.onlinebanking.domain.User;

import java.util.List;

public interface UserService {

    public User save(User user);
    public User findById(Long userId);
    public List<User> findAll();
    public void deleteById(Long userId);

    public User updateById(Long userId);

    public User findByUsername(String name);

    public Boolean isAdmin(User user);
}
