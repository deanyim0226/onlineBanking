package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.Role;
import com.example.onlinebanking.domain.User;
import com.example.onlinebanking.service.RoleService;
import com.example.onlinebanking.service.UserService;
import com.example.onlinebanking.validation.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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

    @Autowired
    UserValidator userValidator;

    //custom validation
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(userValidator);
    }
    @RequestMapping("/userForm")
    public ModelAndView userForm(User user){
        ModelAndView mav = new ModelAndView("userForm");

        mav.addObject("users",userService.findAll());
        mav.addObject("roles",roleService.findAll());
        return mav;
    }

    @RequestMapping("/saveUser")
    public ModelAndView saveUser(@Valid @ModelAttribute User user, BindingResult br){
        ModelAndView mav = new ModelAndView("userForm");

        if(br.hasErrors()){
            System.out.println("error while saving users");
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

        mav.addObject("user",retrievedUser);
        mav.addObject("users",userService.findAll());
        System.out.println(retrievedUser.getRoles());
        mav.addObject("retrievedRole", retrievedUser.getRoles());
        mav.addObject("roles",roleService.findAll());
        /*

         */

        return mav;
    }
}
