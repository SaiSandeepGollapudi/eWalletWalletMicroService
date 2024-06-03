package com.paypal.ewallet.wallet.service;

import com.paypal.ewallet.wallet.domain.Wallet;

public interface WalletService {

    public void createWallet(Long userId);

    public Wallet deleteWallet(Long userId);

}
