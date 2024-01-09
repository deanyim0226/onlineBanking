package com.example.onlinebanking.service;

import com.example.onlinebanking.domain.Account;

import java.util.List;

public interface AccountService {

    public Account saveAccount(Account account);

    public Account findById(Long accountId);

    public List<Account> findAll();

    public List<Account> findAccounts(Long customerId);

    public void deleteById(Long accountId);

    public Account updateAccount(Long accountId);

    /*
        customer
        AccountForm -> system displays account information associated to the specific user only.

        Admin
        AccountForm -> able to perform action to update and delete the account
    */
}
