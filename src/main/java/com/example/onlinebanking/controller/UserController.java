package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.Role;
import com.example.onlinebanking.domain.User;
import com.example.onlinebanking.service.RoleService;
import com.example.onlinebanking.service.UserService;
import com.example.onlinebanking.validation.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserValidator userValidator;

    //custom validation
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(userValidator);
    }
    @RequestMapping("/userForm")
    public ModelAndView userForm(User user, Principal principal){
        ModelAndView mav = new ModelAndView("userForm");

        /*
        service based on admin and customer
        admin should be able to see
         */
        User retrievedUser = userService.findByUsername(principal.getName());

        if(retrievedUser == null){
            //errer
        }

        Boolean isAdmin = userService.isAdmin(retrievedUser);

        if(isAdmin == true){
            mav.addObject("users",userService.findAll());
            mav.addObject("roles",roleService.findAll());
        }else{

            System.out.println("user is not admin");
            mav.addObject("users", Arrays.asList(retrievedUser));
            mav.addObject("roles",roleService.findAll());
        }

        return mav;
    }

    @RequestMapping("/saveUser")
    public ModelAndView saveUser(@Valid @ModelAttribute User user, BindingResult br, Principal principal){
        ModelAndView mav = new ModelAndView("userForm");
        User retrievedUser = userService.findByUsername(principal.getName());
        StringBuilder sb = new StringBuilder("");
        Boolean isAdmin = userService.isAdmin(retrievedUser);

        /*
        when the role is not admin, Id and roles should be disabled because
        Id and roles are changeable through admin.
         */

        System.out.println(user.getUserId());
        System.out.println(user.getRoles());
        System.out.println(user.getEmail());

        if(br.hasErrors()){

            List<FieldError> fieldErrors = br.getFieldErrors();
            for(FieldError fieldError: fieldErrors){
                sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
            }
            System.out.println("sb: " + sb );

            System.out.println("error while saving users");
            //error could lead to two cases
            // one for admin  another for customer
            // it should display differently
            mav.addObject("users",userService.findAll());
            mav.addObject("hasError",true);
            return mav;
        }

        userService.save(user);

        mav.addObject("users",userService.findAll());
        mav.addObject("roles",roleService.findAll());
        mav.setViewName("redirect:userForm");
        return mav;
    }

    @RequestMapping("/deleteUser")
    public ModelAndView deleteUser(User user){
        ModelAndView mav = new ModelAndView("redirect:userForm");
        //userService.deleteUser(user.getUserID();
        //need to work on either using requrestparam or binding object
        return mav;
    }

    @RequestMapping("/updateUser")
    public ModelAndView updateUser(@RequestParam Long userId){
        ModelAndView mav = new ModelAndView("userForm");

        User retrievedUser = userService.updateById(userId);
        Boolean isAdmin = userService.isAdmin(retrievedUser);

        if(isAdmin == true){
            mav.addObject("user",retrievedUser);
            mav.addObject("users",userService.findAll());
            mav.addObject("retrievedRole", retrievedUser.getRoles());
            mav.addObject("roles",roleService.findAll());
        }else{
            mav.addObject("user",retrievedUser);
            mav.addObject("users",Arrays.asList(retrievedUser));
            mav.addObject("retrievedRole", retrievedUser.getRoles());
            mav.addObject("roles",roleService.findAll());
        }

        return mav;
    }



}
