package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.Role;
import com.example.onlinebanking.domain.User;
import com.example.onlinebanking.service.RoleService;
import com.example.onlinebanking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping("/userForm")
    public ModelAndView userForm(User user){
        ModelAndView mav = new ModelAndView("userForm");

        mav.addObject("users",userService.findAll());
        mav.addObject("roles",roleService.findAll());
        return mav;
    }

    @RequestMapping("/saveUser")
    public ModelAndView saveUser(@Valid @ModelAttribute User user, BindingResult bs){
        ModelAndView mav = new ModelAndView("userForm");

        if(bs.hasErrors()){
            System.out.println("error while saving users");
            mav.addObject("users",userService.findAll());
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

        mav.addObject("user",retrievedUser);
        mav.addObject("users",userService.findAll());
        /*

         */

        return mav;
    }
}
