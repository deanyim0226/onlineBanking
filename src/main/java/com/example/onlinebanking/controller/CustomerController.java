package com.example.onlinebanking.controller;


import com.example.onlinebanking.domain.Customer;
import com.example.onlinebanking.domain.Gender;
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

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @Autowired
    CustomerValidator customerValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(customerValidator);
    }
    @RequestMapping("/customerForm")
    public ModelAndView customerForm(Customer customer){
        ModelAndView mav = new ModelAndView("customerForm");
        mav.addObject("customers",customerService.findAll());
        mav.addObject("genders", Gender.values());

        return mav;

    }
    @RequestMapping("/saveCustomer")
    public ModelAndView saveCustomer(@Valid @ModelAttribute Customer customer, BindingResult br){
        ModelAndView mav = new ModelAndView("customerForm");

        if(br.hasErrors()){
            System.out.println("ERROR WHILE SAVING CUSTOMER");
            mav.addObject("hasError",true);
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
    public ModelAndView updateCustomer(Customer customer){
        ModelAndView mav = new ModelAndView("redirect:customerForm");
        // customerService.deleteById(customer.getCustomerId());



        return mav;

    }
}
