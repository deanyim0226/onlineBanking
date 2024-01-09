package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.Role;
import com.example.onlinebanking.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("r")
public class RoleRestController {

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/getRoles" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> restfulGet(){

        List<Role> retrievedRoles = roleService.findAll();

        return new ResponseEntity<>(retrievedRoles, HttpStatus.OK);
    }

    @PostMapping(value = "/saveRole", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> saveRole(@Valid @RequestBody Role role, BindingResult bs){
        System.out.println("saving the role");
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        Role userRole = roleService.findById(role.getRoleId());

        if(userRole != null || bs.hasErrors()){

            if(bs.hasErrors()){
                System.out.println("errors in form either roleId or name");
                return new ResponseEntity<Role>(userRole, headers, HttpStatus.NOT_ACCEPTABLE);
            }

            System.out.println("duplicate ID");
            return new ResponseEntity<Role>(userRole, headers, HttpStatus.CONFLICT);
        }

        Role savedRole = roleService.save(role);
        headers.add("Saved Role", savedRole.getName());

        return new ResponseEntity<Role>(savedRole, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/updateRole", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRole(@Valid @RequestBody Role role){

        Role retrievedRole = roleService.findById(role.getRoleId());

        if(retrievedRole == null){

            return new ResponseEntity<String>("No role with id " + role.getRoleId(), HttpStatus.NOT_FOUND);
        }

        retrievedRole.setName(role.getName());
        roleService.save(retrievedRole);

        return new ResponseEntity<Role>(role,HttpStatus.OK);

    }

    @DeleteMapping(value = "/delete/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId){

        HttpHeaders headers = new HttpHeaders();

        Role role = roleService.findById(roleId);

        if(role == null){
            return new ResponseEntity<Role>(role,HttpStatus.NOT_FOUND);
        }

        roleService.deleteRole(roleId);
        headers.add("Deleted Role", roleId.toString());

        return new ResponseEntity<Role>(role,headers,HttpStatus.OK);

    }

}
