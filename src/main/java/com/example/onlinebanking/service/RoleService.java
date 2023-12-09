package com.example.onlinebanking.service;

import com.example.onlinebanking.dao.RoleRepository;
import com.example.onlinebanking.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RoleService {

    public Role save(Role role);

    public Role findById(Long roleId);

    public List<Role> findAll();

    public void deleteRole(Long roleId);

    public Role updateById(Long roleId);
}
