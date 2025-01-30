package com.barook.walletmanager.CustomeException;

public class WalletTransactionException extends RuntimeException {
    public WalletTransactionException(String message) {
        super(message);
    }
}
