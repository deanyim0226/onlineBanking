package com.example.onlinebanking.dao;

import com.example.onlinebanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
