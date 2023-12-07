package com.example.onlinebanking.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NotEmpty
    private String customerName;

    @Enumerated
    private Gender gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate customerDOB;

    private String customerMobileNo;

    @Embedded
    private Address customerAddress;

    private String customerRealId;

    @OneToMany(mappedBy = "accountCustomer")
    private List<Account> customerAccounts = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "userId")
    private User user;

}
