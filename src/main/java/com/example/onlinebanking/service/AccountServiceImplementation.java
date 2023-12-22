package com.example.onlinebanking.service;

import com.example.onlinebanking.dao.AccountRepository;
import com.example.onlinebanking.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImplementation implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account saveAccount(Account account) {

        return accountRepository.save(account);
    }

    @Override
    public Account findById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> findAccounts(Long customerId) {

        List<Account> accountList = accountRepository.findAll();
        List<Account> customerAccount = new ArrayList<>();
        for(Account account : accountList){

            if(account.getAccountCustomer().getCustomerId() == customerId){
                customerAccount.add(account);
            }
        }
        return customerAccount;
    }

    @Override
    public void deleteById(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public Account updateAccount(Long accountId) {
        return null;
    }


}
