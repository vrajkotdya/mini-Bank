package practical02;

public class Account {
    private static int accCounter = 0;

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static synchronized String generateAccountNumber() {
        return "AC" + String.format("%04d", ++accCounter);
    }

    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = Math.max(0, balance);
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

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void deposit(long amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public boolean withdraw(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        this.balance -= amount;
        return true;
    }

    public boolean transfer(Account to, long amount) {
        if (to != null && this.withdraw(amount)) {
            to.deposit(amount);
            return true;
        }
        return false;
    }
}
