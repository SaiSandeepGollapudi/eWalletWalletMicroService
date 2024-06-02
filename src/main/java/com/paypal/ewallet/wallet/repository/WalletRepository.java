package com.paypal.ewallet.wallet.repository;

import com.paypal.ewallet.wallet.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Optional<Wallet> findByUserId(Long userId);
}
