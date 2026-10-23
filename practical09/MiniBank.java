package practical09;

import java.util.ArrayList;
import java.util.List;

public class MiniBank {
    public static void main(String[] args) {
        Account acc = new Account("AC0001", "JointAccount", 0);
        System.out.println("Initial Balance: " + acc.getBalance());

        List<Thread> threadList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Thread t = new Thread(new AccountWorker(acc, 1000, 1), "WorkerThread-" + i);
            threadList.add(t);
            t.start();
        }

        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }

        System.out.println("\nExpected Balance : 10000");
        System.out.println("Actual Balance   : " + acc.getBalance());
        System.out.println("Thread synchronization succeeded: " + (acc.getBalance() == 10000));
    }
}
