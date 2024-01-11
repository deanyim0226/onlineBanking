package com.example.onlinebanking.service;

import com.example.onlinebanking.dao.AccountRepository;
import com.example.onlinebanking.dao.BankTransactionRepository;
import com.example.onlinebanking.domain.*;
import jakarta.persistence.criteria.From;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

        if(bankTransaction.getBankTransactionAmount() > 0){
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

    }


    @Override
    public List<BankTransaction> searchTransaction(List<BankTransaction> bankTransactions, Search searchInfo) {

        List<BankTransaction> filteredList = new ArrayList<>();

        TransactionType transactionType = searchInfo.getTransactionType();
        String keyWord = searchInfo.getKeyword();
        LocalDate dateFrom = searchInfo.getDateFrom();
        LocalDate dateTo = searchInfo.getDateTo();
        PeriodicalType periodicalType = searchInfo.getPeriodicalType();
        System.out.println("searching ");
        // searchBy type
        for(BankTransaction transaction : bankTransactions){

            System.out.println(transaction);
            //search by type and date
            //          type or date
            if(transaction.getBankTransactionType().equals(transactionType)){

                if(dateFrom != null && dateTo != null){

                    if(checkTransactionDate(dateFrom,dateTo,transaction)){
                        filteredList.add(transaction);
                    }

                }else{
                    filteredList.add(transaction);
                }

            }else{

                if(dateFrom != null && dateTo != null){

                    if(checkTransactionDate(dateFrom,dateTo,transaction)){
                        filteredList.add(transaction);
                    }
                }
            }
        }

        for (BankTransaction test : filteredList){
            System.out.println("first is " + test);
        }
        return filteredList;

    }

    public boolean checkTransactionDate(LocalDate from, LocalDate to, BankTransaction currentTransaction){

        LocalDateTime transactionTimeInfo = currentTransaction.getBankTransactionDateTime();
        LocalDate transactionDate = transactionTimeInfo.toLocalDate();

        if(transactionDate.isAfter(from)){
            if(transactionDate.isBefore(to)){
                return true;
            }
        }

        return false;

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

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatTime = time.format(format);

        bankTransaction.setBankTransactionDateTime(LocalDateTime.parse(formatTime,format));
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

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatTime = time.format(format);

        bankTransaction.setBankTransactionDateTime(LocalDateTime.parse(formatTime,format));
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
                if(amountToDeposit < 0){
                    //throw error saying cannot deposit negative amount
                    break;
                }
                currentBalance += amountToDeposit;
                account.setAccountBalance(currentBalance);
                accountRepository.save(account);
                break;
            }
        }
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatTime = time.format(format);

        bankTransaction.setBankTransactionDateTime(LocalDateTime.parse(formatTime,format));
        bankTransactionRepository.save(bankTransaction);
    }

}
