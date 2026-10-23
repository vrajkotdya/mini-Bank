package model;

import exception.BankException;
import exception.InsufficientFundsException;

public class FixedDepositAccount extends Account {
    private static final long serialVersionUID = 1L;

    public FixedDepositAccount(String ownerName, long balance) {
        super(ownerName, balance);
    }

    @Override
    public double interestRate() {
        return 7.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return false;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws BankException {
        throw new InsufficientFundsException("Fixed deposit is locked and cannot be withdrawn", amount);
    }
}
