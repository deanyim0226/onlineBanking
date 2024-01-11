package com.example.onlinebanking.validation;

import com.example.onlinebanking.dao.AccountRepository;
import com.example.onlinebanking.domain.Account;
import com.example.onlinebanking.domain.BankTransaction;
import com.example.onlinebanking.domain.TransactionType;
import com.example.onlinebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TransactionValidator implements Validator {

    @Autowired
    AccountRepository accountRepository;


    @Override
    public boolean supports(Class<?> clazz) {
        return BankTransaction.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        BankTransaction bankTransaction = (BankTransaction) target;

        double transactionAmount = bankTransaction.getBankTransactionAmount();
        //if amountToDeposit is negative, it should throw an error.
        //need to update money in respect to the deposit amount.
        System.out.println("transactionAmount -> " + transactionAmount );
        if(transactionAmount == 0){
            errors.rejectValue("bankTransactionAmount","bankTransactionAmount.zero","Transaction amount should be greater than 0");
        }
        else if(transactionAmount < 0){
            errors.rejectValue("bankTransactionAmount","bankTransactionAmount.negative","Transaction amount cannot be negative");
        }

        TransactionType transactionType = bankTransaction.getBankTransactionType();
        switch (transactionType){

            case DEPOSIT:
                System.out.println("CHECK VALIDATION FOR DEPOSIT");
                performDeposit(bankTransaction,errors);
                break;
            case WITHDRAWAL:
                System.out.println("CHECK VALIDATION FOR WITHDRAWAL");
                performWithdrawal(bankTransaction,errors);
                break;
            case TRANSFER:
                System.out.println("CHECK VALIDATION FOR TRANSFER");
                performTransfer(bankTransaction,errors);
                break;
            default:
                break;
        }

    }

    public void performTransfer(BankTransaction bankTransaction, Errors errors){

        Long accountNumberToTransfer_From = bankTransaction.getBankTransactionFromAccount();

        double amountToTransfer = bankTransaction.getBankTransactionAmount();
        //if amountToDeposit is negative, it should throw an error.

        List<Account> listAccounts = accountRepository.findAll();
        Account accountFrom = null;

        for(Account account : listAccounts){
            if(account.getAccountId() == accountNumberToTransfer_From){
                accountFrom = account;
            }
        }

        double amountFromAccount = accountFrom.getAccountBalance();

        if( amountFromAccount - amountToTransfer < 0){
            //not enough money
            errors.rejectValue("bankTransactionAmount","bankTransactionAmount.insufficient","Not enough money to transfer");
        }
    }

    public void performWithdrawal(BankTransaction bankTransaction, Errors errors){


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
                    errors.rejectValue("bankTransactionAmount","bankTransactionAmount.insufficient","Not enough money to withdraw");
                    break;
                }
            }
        }

    }
    public void performDeposit(BankTransaction bankTransaction, Errors errors){

    }
}
