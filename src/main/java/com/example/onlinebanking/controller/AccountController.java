package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.Account;
import com.example.onlinebanking.domain.AccountType;
import com.example.onlinebanking.domain.BankTransaction;
import com.example.onlinebanking.domain.TransactionType;
import com.example.onlinebanking.service.AccountService;
import com.example.onlinebanking.service.BankTransactionService;
import com.example.onlinebanking.validation.AccountValidator;
import com.example.onlinebanking.validation.CustomerValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    BankTransactionService bankTransactionService;

    @Autowired
    AccountValidator accountValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(accountValidator);
    }


    @RequestMapping("/accountForm")
    public ModelAndView accountForm(Account account){
        ModelAndView mav = new ModelAndView("accountForm");
        mav.addObject("accounts",accountService.findAll());
        mav.addObject("accountTypes", AccountType.values());
        return mav;
    }

    @RequestMapping("/saveAccount")
    public ModelAndView saveAccount(@Valid @ModelAttribute Account account, BindingResult br){
        ModelAndView mav = new ModelAndView("accountForm");

        if(br.hasErrors()){
            System.out.println("ERROR WHILE SAVING ACCOUNT");
            mav.addObject("hasError",true);
            return mav;
        }

        BankTransaction transaction = new BankTransaction();
        transaction.setBankTransactionAmount(account.getAccountBalance());
        transaction.setBankTransactionDateTime(LocalDateTime.now());
        transaction.setBankTransactionType(TransactionType.NEW_ACCOUNT);
        transaction.setComments("New account is created");

        bankTransactionService.saveTransaction(transaction);
        accountService.saveAccount(account);
        mav.addObject("accounts",accountService.findAll());
        mav.setViewName("redirect:accountForm");
        return mav;
    }

    @RequestMapping("/updateAccount")
    public ModelAndView updateAccount(@RequestParam Long accountId){
        ModelAndView mav = new ModelAndView("accountForm");

        Account retrievedAccount = accountService.findById(accountId);

        mav.addObject("account",retrievedAccount);
        mav.addObject("accounts", accountService.findAll());
        mav.addObject("accountTypes", AccountType.values());
        mav.addObject("selectedAccountType", retrievedAccount.getAccountType());


        return mav;
    }

    @RequestMapping("/deleteAccount")
    public ModelAndView deleteAccount(Account account){
        ModelAndView mav = new ModelAndView("accountForm");
        //accountService.deleteById(account.getAccountId());

        return mav;
    }

}
