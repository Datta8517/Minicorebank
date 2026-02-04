package com.minicorebank.controller;

import com.minicorebank.dto.TransferRequest;
import com.minicorebank.model.TransactionRecord;
import com.minicorebank.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionRecord> transfer(@RequestHeader("Authorization") String authHeader, @RequestBody TransferRequest req, HttpServletRequest request) {
        TransactionRecord tr = transactionService.transfer(authHeader, req.getFromAccountId(), req.getToAccountId(), req.getAmount(), req.getPin(), request);
        return ResponseEntity.ok(tr);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionRecord>> accountTxs(@PathVariable String accountId) {
        return ResponseEntity.ok(transactionService.getAccountTransactions(accountId));
    }
}
