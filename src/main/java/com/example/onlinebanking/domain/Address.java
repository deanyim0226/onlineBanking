package com.example.onlinebanking.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    @NotEmpty
    private String addressLine1;
    @NotEmpty
    private String addressLine2;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    @NotEmpty
    private String country;
    @NotEmpty
    private String zipcode;
}
