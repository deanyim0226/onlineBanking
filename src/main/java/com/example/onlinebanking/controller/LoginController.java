package com.example.onlinebanking.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/register")
    public ModelAndView register(){

        ModelAndView mav = new ModelAndView("/login");


        return mav;
    }

    @RequestMapping({"/login"})
    public String loginToApp(@RequestParam(value = "logout", required = false) String logout,
                             @RequestParam(value = "error", required = false) String error,
                             HttpServletRequest req, HttpServletResponse res, Model model
    ){

        String message = null;

        if(logout != null){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth != null){
                new SecurityContextLogoutHandler().logout(req,res,auth);
                message ="you are logged out";
            }
        }
        System.out.println("---login---");

        if(error != null){
            message =  "either username or password is incorrect";
        }

        model.addAttribute("message",message);

        return "loginForm";
    }
}
