package com.example.onlinebanking.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @NotEmpty
    @Column(name="branchName")
    private String branchName;

    private Address address;

}