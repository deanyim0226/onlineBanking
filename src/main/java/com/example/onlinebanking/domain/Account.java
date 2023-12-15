package com.example.onlinebanking.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Type;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //@DateTimeFormat(pattern = "mm-dd-yyyy")
    private LocalDate accountDateOpened;

    private String accountHolder;

    private double accountBalance;

    @ManyToOne
    @JoinColumn(name = "branchId")
    private Branch accountBranch;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer accountCustomer;
}
