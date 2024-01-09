package com.example.onlinebanking.controller;

import com.example.onlinebanking.domain.BankTransaction;
import com.example.onlinebanking.service.BankTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping("r")
public class TransactionRestController {

    @Autowired
    BankTransactionService bankTransactionService;

    @GetMapping(value = "/getTransactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankTransaction>> getTransactions(){

        List<BankTransaction> retrievedBankTransactions = bankTransactionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(retrievedBankTransactions);

    }

    @PostMapping(value = "/saveTransaction",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankTransaction> saveTransaction(@Valid @RequestBody BankTransaction bankTransaction, BindingResult br){

        BankTransaction retrievedBankTransaction = bankTransactionService.findById(bankTransaction.getBankTransactionId());
        HttpHeaders headers = new HttpHeaders();

        if(retrievedBankTransaction != null || br.hasErrors()){

            if(br.hasErrors()){
                //error while entering transaction info from user
            }

            //error the transaction exist
        }

        bankTransactionService.performTransaction(bankTransaction);
        BankTransaction newTransaction = bankTransactionService.findById(bankTransaction.getBankTransactionId());
        headers.add("new Transaction", newTransaction.getBankTransactionType().toString());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(newTransaction);
    }
}
