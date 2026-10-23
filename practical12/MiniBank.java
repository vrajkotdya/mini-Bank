package practical12;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class MiniBank {
    public static void main(String[] args) {
        AccountStore store = new AccountStore();

        Account a1 = new Account("AC0001", "Riya", 5000);
        Account a2 = new Account("AC0002", "Aman", 2000);
        Account a3 = new Account("AC0003", "Kiran", 9000);

        store.add(a1);
        store.add(a2);
        store.add(a3);

        System.out.println("Lookup AC0002: " + store.get("AC0002"));

        System.out.println("\nAccounts sorted by balance (highest first):");
        ArrayList<Account> sorted = store.sortedByBalance();
        for (Account a : sorted) {
            System.out.println(" - " + a);
        }

        System.out.println("\nComparable compareTo between a1 and a2: " + a1.compareTo(a2));

        ConcurrentHashMap<String, Account> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put(a1.getAccountNumber(), a1);
        concurrentMap.put(a2.getAccountNumber(), a2);
        System.out.println("ConcurrentHashMap size: " + concurrentMap.size());
    }
}
