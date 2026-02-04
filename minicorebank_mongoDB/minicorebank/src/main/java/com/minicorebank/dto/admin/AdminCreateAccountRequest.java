

package com.minicorebank.dto.admin;

import lombok.Data;

@Data
public class AdminCreateAccountRequest {
    private String userId;
    private String type;           // SAVINGS / CURRENT (String)
    private Double initialDeposit; // Optional
}

