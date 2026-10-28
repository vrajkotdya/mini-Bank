package practical08;

import practical08.exception.BankException;
import practical08.exception.InsufficientFundsException;
import practical08.model.Account;
import practical08.util.BankSession;

public class MiniBank {
    public static void main(String[] args) {
        Account a1 = new Account("AC0001", "Riya", 1000);
        Account a2 = new Account("AC0002", "Aman", 500);

        try (BankSession session = new BankSession("SESS-101")) {
            System.out.println("Attempting withdrawal of 5000 from balance 1000...");
            a1.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught InsufficientFundsException: " + e.getMessage() + ", Shortfall: Rs. " + e.getShortfall());
        } catch (BankException e) {
            System.out.println("Caught BankException: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed in main.");
        }

        System.out.println("Final Account State: " + a1);
    }
}