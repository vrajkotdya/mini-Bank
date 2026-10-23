package practical05;

public class MiniBank {
    public static void main(String[] args) {
        Account[] accounts = new Account[3];
        accounts[0] = new SavingsAccount("Riya", 5000, 1000);
        accounts[1] = new CurrentAccount("Aman", 2000, 5000);
        accounts[2] = new FixedDepositAccount("Kiran", 100000);

        for (Account a : accounts) {
            System.out.println("Account: " + a.getAccountNumber() + " (" + a.getOwnerName() + ")");
            System.out.println("Type: " + a.getClass().getSimpleName() + " | Interest Rate: " + a.interestRate() + "%");

            if (a instanceof SavingsAccount sa) {
                System.out.println("-> Min balance required: " + sa.getMinBalance());
            } else if (a instanceof CurrentAccount ca) {
                System.out.println("-> Overdraft allowed up to: " + ca.getOverdraftLimit());
            } else if (a instanceof FixedDepositAccount) {
                System.out.println("-> Withdrawal allowed: " + a.canWithdraw(100));
            }
            System.out.println();
        }
    }
}
