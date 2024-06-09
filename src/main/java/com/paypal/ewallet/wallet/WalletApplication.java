package com.paypal.ewallet.wallet;

import com.paypal.ewallet.wallet.service.WalletService;
import com.paypal.ewallet.wallet.service.resource.WalletTransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class WalletApplication  {
public class WalletApplication implements CommandLineRunner {// when CommandLineRunner interface is implemented, by default
	// after running the program the run method below is executed
	public static void main(String[] args) {
		SpringApplication.run(WalletApplication.class, args);
	}

	@Autowired
	WalletService walletService;

	@Override
	public void run(String... args) throws Exception {
		WalletTransactionRequest walletTransactionRequest= new WalletTransactionRequest();
		walletTransactionRequest.setAmount(10.0);
		walletTransactionRequest.setReceiverId(103L);
		walletTransactionRequest.setSenderId(102L);
		walletTransactionRequest.setTransactionType(TransactionType.TRANSFER.name());
		walletService.performTransaction(walletTransactionRequest);
	}
}