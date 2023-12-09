package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.Role;
import com.example.onlinebanking.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoleController {

    @Autowired
    RoleService roleService;

    @RequestMapping("/roleForm")
    public ModelAndView roleForm(Role role){
        ModelAndView mav = new ModelAndView("roleForm");
        mav.addObject("roles", roleService.findAll());

        return mav;
    }

    @RequestMapping("/saveRole")
    public ModelAndView saveRole(@Valid @ModelAttribute Role role, BindingResult bs){

        ModelAndView mav = new ModelAndView("roleForm");

        if(bs.hasErrors()){
            System.out.println("error while saving roles");
            mav.addObject("roles",roleService.findAll());
            return mav;
        }

        roleService.save(role);
        mav.addObject("roles",roleService.findAll());
        mav.setViewName("redirect:roleForm");
        return mav;

    }

    @RequestMapping("/deleteRole")
    public ModelAndView deleteRole(Role role){
        //Spring will just bind request parameters to class instance
        ModelAndView mav = new ModelAndView("redirect:roleForm");
        //roleService.deleteRole(role.getRoleId());
        //need to work on either using requrestparam or binding object

        return mav;
    }

    @RequestMapping("/updateRole")
    public ModelAndView updateRole(@RequestParam Long roleId){
        ModelAndView mav = new ModelAndView("roleForm");
        Role retrievedRole = roleService.updateById(roleId);

        mav.addObject("roles", roleService.findAll());
        mav.addObject("role",retrievedRole);

        return mav;
    }
}