package com.example.onlinebanking.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Search {


    private String keyword;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private PeriodicalType periodicalType;
    private TransactionType transactionType;
}
