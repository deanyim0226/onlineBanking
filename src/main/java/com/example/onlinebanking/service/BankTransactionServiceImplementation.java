package com.example.onlinebanking.service;

import com.example.onlinebanking.dao.AccountRepository;
import com.example.onlinebanking.dao.BankTransactionRepository;
import com.example.onlinebanking.domain.Account;
import com.example.onlinebanking.domain.BankTransaction;
import com.example.onlinebanking.domain.TransactionType;
import jakarta.persistence.criteria.From;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BankTransactionServiceImplementation implements BankTransactionService{

    @Autowired
    BankTransactionRepository bankTransactionRepository;

    @Autowired
    AccountRepository accountRepository;


    @Override
    public BankTransaction saveTransaction(BankTransaction bankTransaction) {
        return bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public BankTransaction findById(Long bankTransactionId) {
        return bankTransactionRepository.findById(bankTransactionId).orElse(null);
    }

    @Override
    public List<BankTransaction> findAll() {
        return bankTransactionRepository.findAll();
    }

    @Override
    public void performTransaction(BankTransaction bankTransaction) {

        TransactionType transactionType = bankTransaction.getBankTransactionType();

        switch (transactionType){

            case DEPOSIT:
                System.out.println("DEPOSIT");
                performDeposit(bankTransaction);
                break;
            case WITHDRAWAL:
                System.out.println("WITHDRAWAL");
                performWithdrawal(bankTransaction);
                break;
            case TRANSFER:
                System.out.println("TRANSFER");
                performTransfer(bankTransaction);
                break;
            default:
                break;

        }
    }

    public void performTransfer(BankTransaction bankTransaction){

        Long accountNumberToTransfer_From = bankTransaction.getBankTransactionFromAccount();
        Long accountNumberToTransfer_To = bankTransaction.getBankTransactionToAccount();

        double amountToTransfer = bankTransaction.getBankTransactionAmount();
        //if amountToDeposit is negative, it should throw an error.

        List<Account> listAccounts = accountRepository.findAll();
        Account accountFrom = null;
        Account accountTo = null;

        for(Account account : listAccounts){
            if(account.getAccountId() == accountNumberToTransfer_From){
                accountFrom = account;
            }else if(account.getAccountId() == accountNumberToTransfer_To){
                accountTo = account;
            }
        }

        double amountFromAccount = accountFrom.getAccountBalance();
        double amountToAccount = accountTo.getAccountBalance();

        if( amountFromAccount - amountToTransfer < 0){
            //not enough money
        }else{
            amountFromAccount -= amountToTransfer;
            accountFrom.setAccountBalance(amountFromAccount);
            accountRepository.save(accountFrom);

            amountToAccount += amountToTransfer;
            accountTo.setAccountBalance(amountToAccount);
            accountRepository.save(accountTo);
        }

        bankTransaction.setBankTransactionDateTime(LocalDateTime.now());
        bankTransactionRepository.save(bankTransaction);
    }

    public void performWithdrawal(BankTransaction bankTransaction){


        Long accountNumberToWithdraw = bankTransaction.getBankTransactionFromAccount();
        double amountToWithdraw = bankTransaction.getBankTransactionAmount();
        //if amountToDeposit is negative, it should throw an error.

        List<Account> listAccounts = accountRepository.findAll();

        for(Account account : listAccounts){
            if(account.getAccountId() == accountNumberToWithdraw){
                //update account based on deposit money
                double currentBalance = account.getAccountBalance();
                if(currentBalance - amountToWithdraw < 0){
                    //throw error saying that not enough money
                    break;
                }
                currentBalance -= amountToWithdraw;
                account.setAccountBalance(currentBalance);
                accountRepository.save(account);
                break;
            }
        }

        bankTransaction.setBankTransactionDateTime(LocalDateTime.now());
        bankTransactionRepository.save(bankTransaction);
    }
    public void performDeposit(BankTransaction bankTransaction){


        Long accountNumberToDeposit = bankTransaction.getBankTransactionToAccount();
        double amountToDeposit = bankTransaction.getBankTransactionAmount();
        //if amountToDeposit is negative, it should throw an error.

        //need to update money in respect to the deposit amount.
        List<Account> listAccounts = accountRepository.findAll();

        for(Account account : listAccounts){
            if(account.getAccountId() == accountNumberToDeposit){
                //update account based on deposit money
                double currentBalance = account.getAccountBalance();
                currentBalance += amountToDeposit;
                account.setAccountBalance(currentBalance);
                accountRepository.save(account);
                break;
            }
        }

        bankTransaction.setBankTransactionDateTime(LocalDateTime.now());
        bankTransactionRepository.save(bankTransaction);
    }


}
