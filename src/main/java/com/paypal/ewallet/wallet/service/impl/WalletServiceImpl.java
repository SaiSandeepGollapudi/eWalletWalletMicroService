package com.paypal.ewallet.wallet.service.impl;

import com.paypal.ewallet.wallet.domain.Wallet;
import com.paypal.ewallet.wallet.exception.WalletException;
import com.paypal.ewallet.wallet.repository.WalletRepository;
import com.paypal.ewallet.wallet.service.WalletService;
import com.paypal.ewallet.wallet.service.resource.WalletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class WalletServiceImpl implements WalletService {

    private Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    WalletRepository walletRepository;

    @Override
    public void createWallet(Long userId) {
        try{
                    Optional<Wallet> optionalWallet=walletRepository.findByUserId(userId);
                    if(optionalWallet.isPresent()){
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
            Optional<Wallet> optionalWallet=walletRepository.findByUserId(userId);
            if(optionalWallet.isPresent()){
                Wallet wallet=optionalWallet.get();
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
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);
        if (optionalWallet.isPresent()) {
            logger.info("Wallet already exists for user: {}", userId);
            WalletResponse walletResponse = new WalletResponse(optionalWallet.get());
            return walletResponse;
        } else {
            throw new WalletException("EWALLET_WALLET_NOT_FOUND", "wallet not found for the user");
        }
    }


}
