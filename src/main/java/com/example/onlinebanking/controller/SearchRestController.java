package com.example.onlinebanking.controller;


import com.example.onlinebanking.domain.BankTransaction;
import com.example.onlinebanking.domain.Search;
import com.example.onlinebanking.service.BankTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("r")
public class SearchRestController {


    @Autowired
    BankTransactionService bankTransactionService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankTransaction>> getBankTransactions(@Valid @RequestBody Search search){

        return null;
    }
    
}
