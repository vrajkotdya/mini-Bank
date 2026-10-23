package practical11;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MiniBank {
    public static void main(String[] args) {
        Account[] accounts = new Account[3];
        accounts[0] = new Account("AC0001", "Riya", 5000);
        accounts[1] = new Account("AC0002", "Aman", 8000);
        accounts[2] = new Account("AC0003", "Pooja", 12000);

        Path dataFile = Paths.get("practical11_data", "accounts.dat");
        Path logFile = Paths.get("practical11_logs", "activity.log");
        Path reportFile = Paths.get("practical11_logs", "summary.txt");

        System.out.println("Saving accounts to binary file...");
        StatePersister.save(accounts, dataFile);

        System.out.println("Reloading accounts from binary file...");
        Account[] loaded = StatePersister.load(dataFile);
        for (Account a : loaded) {
            System.out.println("Loaded: " + a);
        }

        System.out.println("\nAppending transaction logs...");
        TransactionLog.append(logFile, "DEPOSIT AC0001 500");
        TransactionLog.append(logFile, "WITHDRAW AC0002 200");
        TransactionLog.append(logFile, "DEPOSIT AC0003 1500");

        System.out.println("\nGenerating report via NIO...");
        ReportGenerator.generateReport(Paths.get("practical11_logs"), reportFile);
    }
}
