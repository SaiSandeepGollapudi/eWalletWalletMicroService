package com.paypal.ewallet.wallet.service;

import com.paypal.ewallet.wallet.domain.Wallet;
import com.paypal.ewallet.wallet.service.resource.WalletResponse;
import com.paypal.ewallet.wallet.service.resource.WalletTransactionRequest;

public interface WalletService {

    public void createWallet(Long userId);

    public Wallet deleteWallet(Long userId);

    public WalletResponse getWallet(Long userId);

    public boolean performTransaction(WalletTransactionRequest walletTransactionRequest);//as Transaction is expecting to a
    // return value from  wallet

}
