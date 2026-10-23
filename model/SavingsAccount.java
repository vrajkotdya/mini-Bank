package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    private long minBalance;

    public SavingsAccount(String ownerName, long balance, long minBalance) {
        super(ownerName, balance);
        this.minBalance = minBalance;
    }

    public SavingsAccount(String ownerName, long minBalance) {
        super(ownerName);
        this.minBalance = minBalance;
    }

    public long getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(long minBalance) {
        this.minBalance = minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return (getBalance() - amount) >= minBalance;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (!canWithdraw(amount)) {
            long shortfall = minBalance - (getBalance() - amount);
            if (shortfall < 0) {
                shortfall = amount - getBalance();
            }
            throw new InsufficientFundsException("Cannot breach minimum balance of " + minBalance, shortfall);
        }
        return super.withdraw(amount);
    }
}
