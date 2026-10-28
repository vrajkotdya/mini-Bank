package practical10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor {
    private ExecutorService pool;

    public TransactionProcessor() {
        this.pool = Executors.newFixedThreadPool(4);
    }

    public void submit(Runnable task) {
        pool.execute(task);
    }

    public void stop() {
        pool.shutdown();
        try {
            pool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static class TransactionBuffer {
        private List<String> buffer = new ArrayList<>();
        private int limit;

        public TransactionBuffer(int limit) {
            this.limit = limit;
        }

        public synchronized void produce(String tx) throws InterruptedException {
            while (buffer.size() >= limit) {
                wait();
            }
            buffer.add(tx);
            System.out.println("Produced: " + tx);
            notifyAll();
        }

        public synchronized String consume() throws InterruptedException {
            while (buffer.isEmpty()) {
                wait();
            }
            String tx = buffer.remove(0);
            System.out.println("Consumed: " + tx);
            notifyAll();
            return tx;
        }
    }

    public static void transferDeadlockSafe(Account a, Account b, long amount) {
        Account first = a.getAccountNumber().compareTo(b.getAccountNumber()) < 0 ? a : b;
        Account second = (first == a) ? b : a;

        synchronized (first) {
            synchronized (second) {
                if (a.withdraw(amount)) {
                    b.deposit(amount);
                    System.out.println("Safe transfer completed: Rs. " + amount + " from " + a.getAccountNumber() + " to " + b.getAccountNumber());
                }
            }
        }
    }
}