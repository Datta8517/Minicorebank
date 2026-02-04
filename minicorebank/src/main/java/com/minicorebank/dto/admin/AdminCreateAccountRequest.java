

package com.minicorebank.dto.admin;

import lombok.Data;

@Data
public class AdminCreateAccountRequest {
    private Long userId;
    private String type;           // SAVINGS / CURRENT (String)
    private Double initialDeposit; // Optional
}

