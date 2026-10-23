package service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import model.Account;

public class StatePersister {
    public static void save(Account[] accounts, Path file) {
        try {
            if (file.getParent() != null && !Files.exists(file.getParent())) {
                Files.createDirectories(file.getParent());
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
                oos.writeObject(accounts);
            }
        } catch (IOException e) {
            System.out.println("Failed to save accounts: " + e.getMessage());
        }
    }

    public static Account[] load(Path file) {
        if (!Files.exists(file)) {
            return new Account[0];
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            return (Account[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load accounts: " + e.getMessage());
            return new Account[0];
        }
    }

    public static void saveList(List<Account> list, Path file) {
        Account[] arr = list.toArray(new Account[0]);
        save(arr, file);
    }

    public static List<Account> loadList(Path file) {
        Account[] arr = load(file);
        List<Account> list = new ArrayList<>();
        for (Account a : arr) {
            list.add(a);
        }
        return list;
    }
}
