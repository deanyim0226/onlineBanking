package com.example.onlinebanking.controller;


import com.example.onlinebanking.domain.Account;
import com.example.onlinebanking.domain.BankTransaction;
import com.example.onlinebanking.domain.Customer;
import com.example.onlinebanking.domain.TransactionType;
import com.example.onlinebanking.service.*;
import com.example.onlinebanking.validation.TransactionValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TransactionController {
    /*
    when user is logged in, it displays the user's accounts
    if user is not admin,
     */
    @Autowired
    BankTransactionService bankTransactionService;

    @Autowired
    BranchService branchService;

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    @Autowired
    TransactionValidator transactionValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(transactionValidator);
    }

    @RequestMapping("/atm")
    public ModelAndView atm(BankTransaction bankTransaction , Principal principal){
        ModelAndView mav = new ModelAndView("atm");
        setModelAndView(mav,principal);

        return mav;
    }

    @RequestMapping("/saveTransaction")
    public ModelAndView saveTransaction(@Valid @ModelAttribute BankTransaction bankTransaction, BindingResult br, Principal principal){
        ModelAndView mav = new ModelAndView("atm");

        if(br.hasErrors())
        {
            System.out.println("ERROR WHILE SAVING ACCOUNT");
            /*
            condition check whether account has sufficient fund or not
            */
            mav.addObject("hasError",true);
            setModelAndView(mav,principal);
            return mav;
        }

        bankTransactionService.performTransaction(bankTransaction);
        mav.setViewName("redirect:atm");
        return mav;
    }

    public void setModelAndView(ModelAndView mav, Principal principal){
        mav.addObject("transactionTypes", Arrays.stream(TransactionType.values()).filter(transactionType -> transactionType != TransactionType.NEW_ACCOUNT).collect(Collectors.toList()));
        mav.addObject("loggedInUser", principal.getName());

        //think about scenario based on the role
        Customer customer = customerService.findByName(principal.getName());

        //handle a case where customer is null
        List<Account> accounts = accountService.findAccounts(customer.getCustomerId());

        mav.addObject("accounts",accounts);

        List<BankTransaction> bankTransactions = bankTransactionService.findAll();

        //for customer role
        List<Long> accountNumber = new ArrayList<>();
        for(Account account : accounts){
            accountNumber.add(account.getAccountId());
        }

        List<BankTransaction> existingTransactions = new ArrayList<>();
        for(BankTransaction bTransaction: bankTransactions){

            if(accountNumber.contains(bTransaction.getBankTransactionToAccount()) || accountNumber.contains(bTransaction.getBankTransactionFromAccount())){
                existingTransactions.add(bTransaction);
            }
        }
        System.out.println("atm home " + existingTransactions);
        mav.addObject("transactions", existingTransactions);

    }


}
