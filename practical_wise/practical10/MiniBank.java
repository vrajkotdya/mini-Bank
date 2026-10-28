package practical10;

public class MiniBank {
    public static void main(String[] args) {
        TransactionProcessor processor = new TransactionProcessor();

        Account accA = new Account("AC0001", "Riya", 5000);
        Account accB = new Account("AC0002", "Aman", 5000);

        for (int i = 0; i < 6; i++) {
            final int id = i + 1;
            processor.submit(() -> {
                accA.deposit(100);
                System.out.println("Pool task " + id + " executed on " + Thread.currentThread().getName());
            });
        }

        TransactionProcessor.TransactionBuffer buffer = new TransactionProcessor.TransactionBuffer(2);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    buffer.produce("TXN-" + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    buffer.consume();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
        }

        System.out.println("\nTesting deadlock-safe transfers between A and B:");
        Thread t1 = new Thread(() -> TransactionProcessor.transferDeadlockSafe(accA, accB, 500));
        Thread t2 = new Thread(() -> TransactionProcessor.transferDeadlockSafe(accB, accA, 300));
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }

        processor.stop();

        System.out.println("\nFinal Balance A: " + accA.getBalance());
        System.out.println("Final Balance B: " + accB.getBalance());
    }
}