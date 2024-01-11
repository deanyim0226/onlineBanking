package com.example.onlinebanking.controller;


import com.example.onlinebanking.domain.BankTransaction;
import com.example.onlinebanking.domain.Search;
import com.example.onlinebanking.domain.TransactionType;
import com.example.onlinebanking.service.BankTransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("r")
public class SearchRestController {


    @Autowired
    BankTransactionService bankTransactionService;

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankTransaction>> search(@Valid @RequestBody Search search){


        List<BankTransaction> listOfTransaction = bankTransactionService.findAll();
        List<BankTransaction> filteredBankTransactions = bankTransactionService.searchTransaction(listOfTransaction,search);

        if(filteredBankTransactions.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(filteredBankTransactions);
        }

        return ResponseEntity.status(HttpStatus.OK).body(filteredBankTransactions);

    }

    @GetMapping(value = "/searchByType")
    public ResponseEntity<List<BankTransaction>> searchByTransactionType(@RequestParam String tType){


        if(tType.equals("DEPOSIT") || tType.equals("WITHDRAWAL") || tType.equals("TRANSFER") || tType.equals("NEW_ACCOUNT")){
            TransactionType convertedTransactionType = TransactionType.valueOf(tType);
            List<BankTransaction> listOfTransaction = bankTransactionService.findAll();

            Search newSearch = new Search();
            newSearch.setTransactionType(convertedTransactionType);

            List<BankTransaction> filteredBankTransactions = bankTransactionService.searchTransaction(listOfTransaction,newSearch);

            if(filteredBankTransactions.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(filteredBankTransactions);
            }
            return ResponseEntity.status(HttpStatus.OK).body(filteredBankTransactions);

        }else{
            System.out.println("error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping(value = "/searchByDate")
    public ResponseEntity<List<BankTransaction>> searchByDate(HttpServletRequest httpRequest){

        List<BankTransaction> listOfTransaction = bankTransactionService.findAll();

        Search newSearch = new Search();
        LocalDate convertedFrom = LocalDate.parse(httpRequest.getParameter("from"));
        LocalDate convertedTo = LocalDate.parse(httpRequest.getParameter("to"));
        newSearch.setDateFrom(convertedFrom);
        newSearch.setDateTo((convertedTo));

        List<BankTransaction> filteredBankTransactions = bankTransactionService.searchTransaction(listOfTransaction,newSearch);

        if(filteredBankTransactions.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(filteredBankTransactions);
        }
        return ResponseEntity.status(HttpStatus.OK).body(filteredBankTransactions);

    }
}
