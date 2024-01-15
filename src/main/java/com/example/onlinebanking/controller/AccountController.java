package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.*;
import com.example.onlinebanking.service.AccountService;
import com.example.onlinebanking.service.BankTransactionService;
import com.example.onlinebanking.service.CustomerService;
import com.example.onlinebanking.service.UserService;
import com.example.onlinebanking.validation.AccountValidator;
import com.example.onlinebanking.validation.CustomerValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    BankTransactionService bankTransactionService;

    @Autowired
    AccountValidator accountValidator;

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;


    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(accountValidator);
    }

    @RequestMapping("/accountForm")
    public ModelAndView accountForm(Account account, Principal principal){
        ModelAndView mav = new ModelAndView("accountForm");

        User retrievedUser = userService.findByUsername(principal.getName());

        if(retrievedUser == null){
            System.out.println("User does not exist");
        }

        Boolean isAdmin = userService.isAdmin(retrievedUser);

        if(isAdmin){
            mav.addObject("accounts",accountService.findAll());
            mav.addObject("accountTypes", AccountType.values());
        }else{

            //display only current users account info
            Customer currentCustomer = customerService.findByName(retrievedUser.getUsername());
            List<Account> customerAccounts = accountService.findAccounts(currentCustomer.getCustomerId());
            mav.addObject("accounts",customerAccounts);
            mav.addObject("accountTypes", AccountType.values());

            /*
            Things to do
            retrieve branch Id so that there won't be error while typing id

            For account record
            instead of showing branch id, show branch name
            instead of showing customer id, show customer name

            for admin
            able to search specific account with account Id

             */
        }

        return mav;
    }

    @RequestMapping(value = "/saveAccount")
    public ModelAndView saveAccount(@Valid @ModelAttribute Account account, BindingResult br){
        ModelAndView mav = new ModelAndView("accountForm");

        if(br.hasErrors()){
            System.out.println("ERROR WHILE SAVING ACCOUNT");
            mav.addObject("hasError",true);
            mav.addObject("accounts",accountService.findAll());
            mav.addObject("accountTypes", AccountType.values());
            return mav;
        }
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatTime = time.format(format);

        BankTransaction transaction = new BankTransaction();
        transaction.setBankTransactionAmount(account.getAccountBalance());
        transaction.setBankTransactionDateTime(LocalDateTime.parse(formatTime,format));
        transaction.setBankTransactionType(TransactionType.NEW_ACCOUNT);
        transaction.setComments("New account is created");

        bankTransactionService.saveTransaction(transaction);
        //set account opened date

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
