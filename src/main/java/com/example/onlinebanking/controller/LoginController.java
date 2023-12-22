package com.example.onlinebanking.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping({"/home","/"})
    public ModelAndView home(Principal principal){

        ModelAndView mav = new ModelAndView("home");
        mav.addObject("user",principal.getName());
        System.out.println("user is " +principal.getName());
        return mav;
    }
}
