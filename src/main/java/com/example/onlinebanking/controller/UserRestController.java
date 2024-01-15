package com.example.onlinebanking.controller;


import com.example.onlinebanking.domain.User;
import com.example.onlinebanking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("r")
public class UserRestController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/getUser/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long userId){
        return userService.findById(userId);
    }

    @GetMapping(value = "/getUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers(){
        List<User> userList = userService.findAll();
        return new ResponseEntity<List<User>>(userList,HttpStatus.OK);
    }

    @PostMapping(value = "/saveUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user, BindingResult br){

        User retrievedUser = userService.findById(user.getUserId());
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        if(retrievedUser != null || br.hasErrors()){

            if(br.hasErrors()){
                System.out.println("error while entering user info");

                List<FieldError> fieldErrors = br.getFieldErrors();
                for(FieldError fieldError: fieldErrors){
                    sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
                }
                System.out.println("sb: " + sb );
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(retrievedUser);
            }

            System.out.println("duplicate user found while saving user");
            sb.append("User already exist");
            headers.add("errors",sb.toString());

            return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).body(retrievedUser);
        }

        User newUser = userService.save(user);
        headers.add("saved User", newUser.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user, BindingResult br){

        User retrievedUser = userService.findById(user.getUserId());
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder();

        if(retrievedUser == null || br.hasErrors()){
            //user is not found
            if(br.hasErrors()){
                System.out.println("error while entering user info");

                List<FieldError> fieldErrors = br.getFieldErrors();
                for(FieldError fieldError: fieldErrors){
                    sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
                }
                System.out.println("sb: " + sb );
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(retrievedUser);
            }

            System.out.println("duplicate user found while saving user");
            sb.append("User does not exist");
            headers.add("errors",sb.toString());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(retrievedUser);

        }

        retrievedUser.setEmail(user.getEmail());
        retrievedUser.setUsername(user.getUsername());
        retrievedUser.setPassword(user.getPassword());
        retrievedUser.setRoles(user.getRoles());

        User updatedUser = userService.save(retrievedUser);
        headers.add("updated User", retrievedUser.getUsername());
        return  ResponseEntity.status(HttpStatus.OK).headers(headers).body(updatedUser);

    }

    @DeleteMapping(value = "/deleteUser/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(@PathVariable Long userId){

        User retrievedUser = userService.findById(userId);
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        if(retrievedUser == null){
            //user is not found

            sb.append("User does not exist");
            headers.add("errors",sb.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(retrievedUser);

        }

        userService.deleteById(userId);
        headers.add("deleted User", retrievedUser.getUsername());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(retrievedUser);
    }
}
