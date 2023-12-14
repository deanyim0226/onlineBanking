package com.example.onlinebanking.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @NotEmpty
    @Column(name="branchName")
    private String branchName;

    @Embedded
    //@Valid
    private Address address;

    @OneToMany(mappedBy = "accountBranch")
    private List<Account> branchAccount = new ArrayList<>();;

}