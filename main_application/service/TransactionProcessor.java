package service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import exception.BankException;
import model.Account;

public class TransactionProcessor {
    private ExecutorService pool;
    private ConcurrentHashMap<String, Account> sharedAccounts = new ConcurrentHashMap<>();

    public TransactionProcessor() {
        this.pool = Executors.newFixedThreadPool(4);
    }

    public void registerAccount(Account acc) {
        if (acc != null) {
            sharedAccounts.put(acc.getAccountNumber(), acc);
        }
    }

    public Account getAccount(String accNo) {
        return sharedAccounts.get(accNo);
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
        private List<String> list = new ArrayList<>();
        private int capacity;

        public TransactionBuffer(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void produce(String tx) throws InterruptedException {
            while (list.size() >= capacity) {
                wait();
            }
            list.add(tx);
            notifyAll();
        }

        public synchronized String consume() throws InterruptedException {
            while (list.isEmpty()) {
                wait();
            }
            String tx = list.remove(0);
            notifyAll();
            return tx;
        }

        public synchronized int size() {
            return list.size();
        }
    }

    public static void transferSafe(Account a, Account b, long amt) throws BankException {
        Account first;
        Account second;
        if (a.getAccountNumber().compareTo(b.getAccountNumber()) < 0) {
            first = a;
            second = b;
        } else {
            first = b;
            second = a;
        }

        synchronized (first) {
            synchronized (second) {
                a.transfer(b, amt);
            }
        }
    }
}
