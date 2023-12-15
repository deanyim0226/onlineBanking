package com.example.onlinebanking.service;

import com.example.onlinebanking.domain.Account;

import java.util.List;

public interface AccountService {

    public Account saveAccount(Account account);

    public Account findById(Long accountId);

    public List<Account> findAll();

    public void deleteById(Long accountId);

    public Account updateAccount(Long accountId);
}
