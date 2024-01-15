package com.example.onlinebanking.controller;


import com.example.onlinebanking.domain.Customer;
import com.example.onlinebanking.domain.Gender;
import com.example.onlinebanking.domain.User;
import com.example.onlinebanking.service.CustomerService;
import com.example.onlinebanking.service.UserService;
import com.example.onlinebanking.validation.CustomerValidator;
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

import java.security.Principal;
import java.util.Arrays;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerValidator customerValidator;

    @Autowired
    UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(customerValidator);
    }
    @RequestMapping("/customerForm")
    public ModelAndView customerForm(Customer customer, Principal principal){
        ModelAndView mav = new ModelAndView("customerForm");

        User retrievedUser = userService.findByUsername(principal.getName());

        if(retrievedUser == null){
            System.out.println("User does not exist, it should git error");
        }

        Boolean isAdmin = userService.isAdmin(retrievedUser);

        if(isAdmin){
            mav.addObject("customers",customerService.findAll());
            mav.addObject("genders", Gender.values());
        }else{
            mav.addObject("customers", Arrays.asList(customerService.findByName(retrievedUser.getUsername())));
            mav.addObject("genders", Gender.values());
        }

        return mav;
    }
    @RequestMapping("/saveCustomer")
    public ModelAndView saveCustomer(@Valid @ModelAttribute Customer customer, BindingResult br){
        ModelAndView mav = new ModelAndView("customerForm");
        /*
        display available userId
        unused UserId
         */
        if(br.hasErrors()){
            System.out.println("ERROR WHILE SAVING CUSTOMER");
            mav.addObject("hasError",true);
            mav.addObject("customers",customerService.findAll());
            mav.addObject("genders", Gender.values());
            return mav;
        }
        customerService.saveCustomer(customer);
        mav.addObject("customers",customerService.findAll());

        mav.setViewName("redirect:customerForm");

        return mav;

    }

    @RequestMapping("/updateCustomer")
    public ModelAndView updateCustomer(@RequestParam Long customerId){
        ModelAndView mav = new ModelAndView("customerForm");

        Customer retrievedCustomer = customerService.findById(customerId);

        mav.addObject("customer",retrievedCustomer);
        mav.addObject("customers",customerService.findAll());
        mav.addObject("genders", Gender.values());
        mav.addObject("selectedGender", retrievedCustomer.getGender());

        return mav;

    }

    @RequestMapping("/deleteCustomer")
    public ModelAndView deleteCustomer(Customer customer){
        ModelAndView mav = new ModelAndView("redirect:customerForm");
        customerService.deleteById(customer.getCustomerId());
        return mav;

    }
}
