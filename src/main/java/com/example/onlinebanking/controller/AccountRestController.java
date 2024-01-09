package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.Account;
import com.example.onlinebanking.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("r")
public class AccountRestController {

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/getAccounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Account>> getAccount(){
        List<Account> retrievedAccounts = accountService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(retrievedAccounts);
    }

    @PostMapping(value = "/saveAccount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> saveAccount(@Valid @RequestBody Account account, BindingResult br){

        Account retrievedAccount = accountService.findById(account.getAccountId());
        HttpHeaders headers = new HttpHeaders();

        if(retrievedAccount != null || br.hasErrors()){

            if(br.hasErrors()){
                //error while entering account info from user
            }
            //duplicate account found
        }

        Account newAccount = accountService.saveAccount(account);
        headers.add("saved Account", newAccount.getAccountHolder());

        return ResponseEntity.status(HttpStatus.OK).body(newAccount);
    }

    @PutMapping(value = "/updateAccount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateAccount(@Valid @RequestBody Account account, BindingResult br){

        Account retrievedAccount = accountService.findById(account.getAccountId());
        HttpHeaders headers = new HttpHeaders();

        if(retrievedAccount == null || br.hasErrors()){
            if(br.hasErrors()){
                //error while entering account info from user
            }

            //user does not exist
        }

        retrievedAccount.setAccountBranch(account.getAccountBranch());
        retrievedAccount.setAccountType(account.getAccountType());
        retrievedAccount.setAccountCustomer(account.getAccountCustomer());
        retrievedAccount.setAccountDateOpened(account.getAccountDateOpened());
        retrievedAccount.setAccountHolder(account.getAccountHolder());

        Account updatedAccount = accountService.saveAccount(retrievedAccount);
        headers.add("updated Account", updatedAccount.getAccountHolder());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(updatedAccount);
    }

    @DeleteMapping(value = "/deleteAccount/{accountId}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long accountId){

        Account retrievedAccount = accountService.findById(accountId);

        if(retrievedAccount == null){
            // account does not exist
            return null;
        }
        accountService.deleteById(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(retrievedAccount);
    }

}
