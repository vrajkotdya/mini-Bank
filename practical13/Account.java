package practical13;

public class Account {
    private String accNo;
    private String name;
    private long balance;

    public Account(String accNo, String name, long balance) {
        this.accNo = accNo;
        this.name = name;
        this.balance = balance;
    }

    public String getAccNo() {
        return accNo;
    }

    @Override
    public String toString() {
        return accNo + " | " + name + " | " + balance;
    }
}
