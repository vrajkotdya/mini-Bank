package practical07.model;

import practical07.model.annotation.Id;
import practical07.model.annotation.Positive;
import practical07.model.annotation.MaxLength;

public class Account {
    @Id
    private final String accountNumber;

    @MaxLength(10)
    private String ownerName;

    @Positive(message = "must be > 0")
    private long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }
}
