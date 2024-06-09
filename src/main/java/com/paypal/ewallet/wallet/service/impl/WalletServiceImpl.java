package com.paypal.ewallet.wallet.service.impl;

import com.paypal.ewallet.wallet.domain.Wallet;
import com.paypal.ewallet.wallet.exception.WalletException;
import com.paypal.ewallet.wallet.repository.WalletRepository;
import com.paypal.ewallet.wallet.service.WalletService;
import com.paypal.ewallet.wallet.service.resource.WalletResponse;
import com.paypal.ewallet.wallet.service.resource.WalletTransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.util.Objects;
import java.util.Optional;
@Service
public class WalletServiceImpl implements WalletService {

    private Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    WalletRepository walletRepository;

    @Override
    public void createWallet(Long userId) {
        try{
            Wallet userWallet=walletRepository.findByUserId(userId);
            if(Objects.nonNull(userWallet)){// Check if the wallet object is not null
                        logger.info("Wallet already exists for user: {}",userId);
                        return;
                    }
Wallet wallet= new Wallet();// the default kafka delivery semantics is "atleast once" so it might create duplicate message,
//  i.e more than one 1 wallet can occur so we  handle that exception below such that no duplicate wallet is created
wallet.setUserId(userId);
wallet.setBalance(0.0);
            walletRepository.save(wallet);

        }
        catch (Exception ex){
            logger.error("Exception while creating wallet: {} ",ex.getMessage());//{} is a placeholder used for string
            // interpolation, where ex.getMessage() will be inserted into the log message at runtime.
            //Use {}: Preferred for its efficiency and simplicity. The logging framework handles the interpolation only
            // if the log level is enabled, avoiding unnecessary computation..Avoid String.format in
            // Logging: Less efficient because it performs string formatting regardless of whether
            // the log statement is needed, leading to unnecessary performance overhead.
        }
    }
    @Override
    public Wallet deleteWallet(Long userId) {
        try{
            Wallet wallet=walletRepository.findByUserId(userId);
            if(Objects.nonNull(wallet)){// Check if the wallet object is not null
                walletRepository.delete(wallet);
                return wallet;
            }else{
                logger.info("Wallet does not exist for user: {}",userId);
                return null;
            }
        }
        catch (Exception ex){
            logger.error("Exception while deleting wallet: {} ",ex.getMessage());
            return null;
        }
}

    @Override
    public WalletResponse getWallet(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (Objects.nonNull(wallet)) {
            logger.info("Wallet already exists for user: {}", userId);
            WalletResponse walletResponse = new WalletResponse(optionalWallet.get());
            return walletResponse;
        } else {
            throw new WalletException("EWALLET_WALLET_NOT_FOUND", "wallet not found for the user");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = WalletException.class,noRollbackFor = NullPointerException.class)
    public boolean performTransaction(WalletTransactionRequest walletTransactionRequest) {
        Wallet senderWallet = walletRepository.findByUserId(walletTransactionRequest.getSenderId());
        Wallet receiverWallet = walletRepository.findByUserId(walletTransactionRequest.getReceiverId());

        if(TransactionType.DEPOSIT.name().equals(walletTransactionRequest.getTransactionType())){
            if (Objects.isNull(receiverWallet)) {
                throw new WalletException("EWALLET_WALLET_NOT_FOUND", "wallet not found for the user");
            }
            updateWallet(receiverWallet,walletTransactionRequest.getAmount());
            return true;
        }
        else if(TransactionType.WITHDRAW.name().equals(walletTransactionRequest.getTransactionType())){
            if (Objects.isNull(receiverWallet)) {
                throw new WalletException("EWALLET_WALLET_NOT_FOUND", "wallet not found for the user");
            }
            updateWallet(receiverWallet,-1 * walletTransactionRequest.getAmount());
            return true;
        }
        else if(TransactionType.TRANSFER.name().equals(walletTransactionRequest.getTransactionType())) {
            try {
                if (Objects.isNull(senderWallet) || Objects.isNull(receiverWallet)) {
                    throw new WalletException("EWALLET_WALLET_NOT_FOUND", "wallet not found for the user");
                }
                handleTransaction(senderWallet, receiverWallet, walletTransactionRequest.getAmount());
                return true;
            } catch (WalletException exception) {

                logger.error("Exception while performing transaction: {} ", exception.getMessage());

                throw exception;
            }
        }
        else{
            throw  new WalletException("EWALLET_INVALID_TRANSACTION_TYPE","Invalid transaction type");
        }
    }


}
