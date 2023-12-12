package com.example.onlinebanking.controller;


import com.example.onlinebanking.domain.Customer;
import com.example.onlinebanking.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping("/customerForm")
    public ModelAndView customerForm(Customer customer){
        ModelAndView mav = new ModelAndView("customerForm");
        mav.addObject("customers",customerService.findAll());

        return mav;

    }
    @RequestMapping("/saveCustomer")
    public ModelAndView customerForm(@Valid @ModelAttribute Customer customer, BindingResult br){
        ModelAndView mav = new ModelAndView("customerForm");

        if(br.hasErrors()){
            System.out.println("ERROR WHILE SAVING CUSTOMER");
            return mav;
        }
        customerService.saveCustomer(customer);
        mav.addObject("customers",customerService.findAll());


        return mav;

    }
}
