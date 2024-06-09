package com.paypal.ewallet.wallet.controller;

import com.paypal.ewallet.wallet.service.WalletService;
import com.paypal.ewallet.wallet.service.resource.WalletTransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class WalletController {
    @Autowired
    WalletService walletService;

    @GetMapping("wallet/{user-id}")
    public ResponseEntity<?> getWallet(@PathVariable("user-id") Long userId){
        return new ResponseEntity<>(walletService.getWallet(userId), HttpStatus.OK);
    }

    @PostMapping("wallet/transaction")
    public ResponseEntity<Boolean> performTransaction(@RequestBody WalletTransactionRequest walletTransactionRequest){
       //even if we use Boolean we won't be returning true/false, will return statusCode only, Boolean is used for simplicity
        boolean success = walletService.performTransaction(walletTransactionRequest);
        if(success)
            return new ResponseEntity<>(success,HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(success,HttpStatus.BAD_REQUEST);
    }

}
