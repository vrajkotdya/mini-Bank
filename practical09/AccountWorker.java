package practical09;

public class AccountWorker implements Runnable {
    private Account account;
    private int times;
    private long amount;

    public AccountWorker(Account account, int times, long amount) {
        this.account = account;
        this.times = times;
        this.amount = amount;
    }

    @Override
    public void run() {
        System.out.println("Started thread: " + Thread.currentThread().getName());
        for (int i = 0; i < times; i++) {
            account.deposit(amount);
        }
        System.out.println("Finished thread: " + Thread.currentThread().getName());
    }
}
