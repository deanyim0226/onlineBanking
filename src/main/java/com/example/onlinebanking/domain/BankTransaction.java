package com.example.onlinebanking.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankTransactionId;

    private Long bankTransactionFromAccount;

    private Long bankTransactionToAccount;

    private double bankTransactionAmount;

    @Enumerated(EnumType.STRING)
    private TransactionType bankTransactionType;

    private LocalDateTime bankTransactionDateTime;

    private String comments;
}
