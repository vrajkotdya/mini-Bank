package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    private long overdraftLimit;

    public CurrentAccount(String ownerName, long balance, long overdraftLimit) {
        super(ownerName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public CurrentAccount(String ownerName, long overdraftLimit) {
        super(ownerName);
        this.overdraftLimit = overdraftLimit;
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(long overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return (getBalance() - amount) >= -overdraftLimit;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (!canWithdraw(amount)) {
            long shortfall = (amount - getBalance()) - overdraftLimit;
            throw new InsufficientFundsException("Overdraft limit of " + overdraftLimit + " exceeded", shortfall);
        }
        return super.withdraw(amount);
    }
}
