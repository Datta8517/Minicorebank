package com.minicorebank.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountRequest {
    private String type;
    private Double initialDeposit;


}
