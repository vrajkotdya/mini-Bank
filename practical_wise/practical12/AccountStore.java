package practical12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class AccountStore {
    private HashMap<String, Account> map = new HashMap<>();
    private ArrayList<String> history = new ArrayList<>();

    public void add(Account acc) {
        if (acc != null) {
            map.put(acc.getAccountNumber(), acc);
        }
    }

    public Account get(String accNo) {
        return map.get(accNo);
    }

    public ArrayList<Account> getAll() {
        return new ArrayList<>(map.values());
    }

    public void addHistory(String entry) {
        history.add(entry);
    }

    public ArrayList<String> getHistory() {
        return new ArrayList<>(history);
    }

    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> list = new ArrayList<>(map.values());
        list.sort(new Comparator<Account>() {
            @Override
            public int compare(Account a1, Account a2) {
                return Long.compare(a2.getBalance(), a1.getBalance());
            }
        });
        return list;
    }
}