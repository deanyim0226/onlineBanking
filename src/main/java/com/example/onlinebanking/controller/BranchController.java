package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.Branch;
import com.example.onlinebanking.service.BranchService;
import com.example.onlinebanking.validation.BranchValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BranchController {

    @Autowired
    BranchService branchService;

    @Autowired
    BranchValidator branchValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(branchValidator);
    }

    @RequestMapping("/branchForm")
    public ModelAndView branchForm(Branch branch){

        ModelAndView mav = new ModelAndView("branchForm");
        mav.addObject("branches", branchService.findAll());

        return mav;
    }

    @RequestMapping("/saveBranch")
    public ModelAndView saveBranch(@Valid @ModelAttribute Branch branch, BindingResult br){

        ModelAndView mav = new ModelAndView("branchForm");

        if(br.hasErrors()){
            System.out.println("error while saving branch");
            mav.addObject("hasError",true);
            mav.addObject("branches", branchService.findAll());

            return mav;
        }

        branchService.saveBranch(branch);
        mav.addObject("branches", branchService.findAll());
        mav.setViewName("redirect:branchForm");
        return mav;
    }

    @RequestMapping("/deleteBranch")
    public ModelAndView deleteBranch(Branch branch){
        ModelAndView mav = new ModelAndView("branchForm");
        //branchService.deleteById(branch.getBranchId());

        return mav;
    }

    @RequestMapping("/updateBranch")
    public ModelAndView updateBranch(@RequestParam Long branchId){
        ModelAndView mav = new ModelAndView("branchForm");

        Branch retrievedBranch = branchService.findById(branchId);

        mav.addObject("branch",retrievedBranch);
        mav.addObject("branches", branchService.findAll());
        return mav;

    }


}
