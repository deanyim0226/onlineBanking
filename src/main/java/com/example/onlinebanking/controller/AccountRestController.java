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
import org.springframework.validation.FieldError;
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
        StringBuilder sb = new StringBuilder("");

        if(retrievedAccount != null || br.hasErrors()){

            if(br.hasErrors()){
                //error while entering account info from user
                List<FieldError> fieldErrors = br.getFieldErrors();
                for(FieldError fieldError: fieldErrors){
                    sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
                }
                System.out.println("sb: " + sb );
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(retrievedAccount);
            }else{
                //user does not exist
                sb.append("Account already exist");
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).body(retrievedAccount);
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
        StringBuilder sb = new StringBuilder("");

        if(retrievedAccount == null || br.hasErrors()){
            if(br.hasErrors()){
                //error while entering account info from user
                List<FieldError> fieldErrors = br.getFieldErrors();
                for(FieldError fieldError: fieldErrors){
                    sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
                }
                System.out.println("sb: " + sb );
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(retrievedAccount);
            }else{
                //user does not exist
                sb.append("Account does not exist");
                headers.add("errors",sb.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(retrievedAccount);
            }
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
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        if(retrievedAccount == null){
            //account does not exist
            sb.append("Account does not exist");
            headers.add("errors", sb.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(retrievedAccount);
        }

        accountService.deleteById(accountId);
        headers.add("deleted Account", retrievedAccount.getAccountHolder());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(retrievedAccount);
    }

}
