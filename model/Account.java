package model;

import java.io.Serializable;
import java.util.Objects;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import exception.AccountNotFoundException;
import model.annotation.Id;
import model.annotation.Positive;

public abstract class Account implements Transactable, InterestBearing, Comparable<Account>, Serializable {
    private static final long serialVersionUID = 1L;
    private static int accountCounter = 0;

    @Id
    private final String accountNumber;
    private String ownerName;

    @Positive(message = "must be > 0")
    private long balance;
    private boolean active;

    private static synchronized String generateAccountNumber() {
        return "AC" + String.format("%04d", ++accountCounter);
    }

    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    @Override
    public synchronized void deposit(long amount) throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }
        this.balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (!canWithdraw(amount)) {
            long shortfall = amount - this.balance;
            if (shortfall < 0) {
                shortfall = 0;
            }
            throw new InsufficientFundsException("Insufficient balance", shortfall);
        }
        this.balance -= amount;
        return true;
    }

    public void transfer(Account to, long amount) throws BankException {
        if (to == null) {
            throw new AccountNotFoundException("Destination account not found");
        }
        boolean ok = false;
        try {
            this.withdraw(amount);
            to.deposit(amount);
            ok = true;
        } catch (BankException e) {
            throw e;
        } finally {
            if (!ok) {
                System.out.println("Transfer failed between " + this.accountNumber + " and " + to.getAccountNumber());
            }
        }
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance + (active ? " | Active" : " | Inactive");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account a)) return false;
        return Objects.equals(this.accountNumber, a.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }
}
