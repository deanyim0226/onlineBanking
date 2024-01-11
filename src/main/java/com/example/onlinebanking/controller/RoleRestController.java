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
import org.springframework.validation.FieldError;
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
    public ResponseEntity<Role> saveRole(@Valid @RequestBody Role role, BindingResult br){
        System.out.println("saving the role");

        Role retrievedRole = roleService.findById(role.getRoleId());
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        if(retrievedRole != null || br.hasErrors()){

            if(br.hasErrors()){
                System.out.println("errors in form either roleId or name");

                List<FieldError> fieldErrors = br.getFieldErrors();
                for(FieldError fieldError: fieldErrors){
                    sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
                }
                System.out.println("sb: " + sb );
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(retrievedRole);
            }

            System.out.println("duplicate ID");
            sb.append("Role already exist");
            headers.add("errors",sb.toString());

            return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).body(retrievedRole);
        }

        Role savedRole = roleService.save(role);
        headers.add("Saved Role", savedRole.getName());

        return new ResponseEntity<Role>(savedRole, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/updateRole", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRole(@Valid @RequestBody Role role, BindingResult br){

        Role retrievedRole = roleService.findById(role.getRoleId());
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        if(retrievedRole == null || br.hasErrors()){

            if(br.hasErrors()){
                System.out.println("errors in form either roleId or name");

                List<FieldError> fieldErrors = br.getFieldErrors();
                for(FieldError fieldError: fieldErrors){
                    sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
                }
                System.out.println("sb: " + sb );
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(retrievedRole);
            }

            sb.append("Role does not exist");
            headers.add("errors",sb.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(retrievedRole);
        }

        retrievedRole.setName(role.getName());
        roleService.save(retrievedRole);

        return new ResponseEntity<Role>(role,HttpStatus.OK);

    }

    @DeleteMapping(value = "/delete/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId){


        Role retrievedRole = roleService.findById(roleId);
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        if(retrievedRole == null){
            sb.append("Role does not exist");
            headers.add("errors",sb.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(retrievedRole);
        }

        roleService.deleteRole(roleId);
        headers.add("Deleted Role", roleId.toString());

        return new ResponseEntity<Role>(retrievedRole,headers,HttpStatus.OK);

    }

}
