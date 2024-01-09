package com.example.onlinebanking.controller;


import com.example.onlinebanking.domain.Search;
import com.example.onlinebanking.domain.*;
import com.example.onlinebanking.service.AccountService;
import com.example.onlinebanking.service.BankTransactionService;
import com.example.onlinebanking.service.CustomerService;
import com.example.onlinebanking.service.UserService;
import jakarta.validation.Valid;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;

    @Autowired
    BankTransactionService bankTransactionService;


    @RequestMapping("/searchForm")
    private ModelAndView searchForm(Search search, Principal principal){

        ModelAndView mav = new ModelAndView("searchForm");

        String userName = principal.getName();
        User currentUser = userService.findByUsername(userName);
        Boolean isAdmin = userService.isAdmin(currentUser);

        if(isAdmin){
            mav.addObject("filteredTransactions",bankTransactionService.findAll());
        }

        mav.addObject("transactionTypes", Arrays.stream(TransactionType.values()).filter(type -> type != type.NEW_ACCOUNT).collect(Collectors.toList()));
        mav.addObject("periodicalTypes",PeriodicalType.values());

        return mav;
    }

    @RequestMapping("/searchTransaction")
    private ModelAndView searchTransaction(@Valid @ModelAttribute Search search, Principal principal){

        ModelAndView mav = new ModelAndView("searchForm");

        mav.addObject("transactionTypes", Arrays.stream(TransactionType.values()).filter(type -> type != type.NEW_ACCOUNT).collect(Collectors.toList()));
        mav.addObject("periodicalTypes",PeriodicalType.values());


        String userName = principal.getName();
        //think about scenario based on the role
        Customer customer = customerService.findByName(userName);
        //to check the role of currentUser
        User currentUser = userService.findByUsername(userName);
        Boolean isAdmin = userService.isAdmin(currentUser);


        List<BankTransaction> bankTransactions = bankTransactionService.findAll();

        if(isAdmin == true){
            /*
            able to see all the transactions
             */
            List<BankTransaction> filteredTransactions = bankTransactionService.searchTransaction(bankTransactions,search);
            mav.addObject("filteredTransactions", filteredTransactions);

        }else{

            /*
            able to see user's transaction
            search by transaction type
            search by specific date
            dayly
            weekly
            monthly
             */

            //handle a case where customer is null
            List<Account> accounts = accountService.findAccounts(customer.getCustomerId());

            if(accounts == null){
                //user does not have an account, so it should throw an error message
            }

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

            List<BankTransaction> filteredTransactions = bankTransactionService.searchTransaction(existingTransactions,search);
            mav.addObject("filteredTransactions", filteredTransactions);
        }

        return mav;
    }
}
