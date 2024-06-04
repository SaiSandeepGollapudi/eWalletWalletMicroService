package com.paypal.ewallet.wallet.service.resource;


import com.paypal.ewallet.wallet.domain.Wallet;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletResponse {

    // whenever you're giving a response which are of non-connected entities.
    // You should never connect them in your entity class. You will always connect them in your response class like this when
    // you're not using mapping like oneToMany etc
//Data Transfer Object (DTO): The UserResponse class is a DTO used to send data to clients.
//Decoupling: Separates the internal entity structure (User) from the response structure (UserResponse).
    //Custom Formatting: Allows for custom formatting or transformations, such as converting id to a string.
    //API Stability: Provides a stable API response format that can remain consistent even if the User entity changes.
    private Long userId;
    private Long walletId;
    private Double balance;

    public WalletResponse(Wallet wallet){
        this.userId=wallet.getUserId();
        this.walletId=wallet.getId();
        this.balance=wallet.getBalance();
    }
    }
