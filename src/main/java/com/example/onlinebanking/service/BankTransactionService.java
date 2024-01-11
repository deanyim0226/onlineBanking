package com.example.onlinebanking.service;

import com.example.onlinebanking.domain.BankTransaction;
import com.example.onlinebanking.domain.Search;
import org.springframework.validation.Errors;

import java.util.List;

public interface BankTransactionService {

    public BankTransaction saveTransaction(BankTransaction bankTransaction);

    public BankTransaction findById(Long bankTransactionId);

    public List<BankTransaction> findAll();

    public void performTransaction(BankTransaction bankTransaction);

    public List<BankTransaction> searchTransaction(List<BankTransaction> bankTransaction, Search searchInfo);


}
