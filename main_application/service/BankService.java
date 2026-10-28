package service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidAmountException;
import model.Account;
import model.AccountWorker;
import model.BankInfo;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import model.annotation.Audit;
import util.AuditLogger;
import util.StatementFormatter;
import util.Validator;

public class BankService {
    private Repository<Account> repo = new Repository<>();
    private TransactionProcessor processor = new TransactionProcessor();
    private BankInfo bankInfo = new BankInfo("CHARUSAT Bank", "Changa Branch");
    private Path dataFile = Paths.get("data", "accounts.dat");
    private Path logsDir = Paths.get("logs");
    private Path txLogFile = Paths.get("logs", "transactions.log");
    private Path reportFile = Paths.get("logs", "daily_report.txt");

    public BankService() {
        loadData();
    }

    public BankInfo getBankInfo() {
        return bankInfo;
    }

    @Audit(action = "OPEN_ACCOUNT")
    public SavingsAccount openSavingsAccount(String name, long balance, long minBal) throws BankException {
        if (name == null || name.trim().isEmpty()) {
            throw new BankException("Account holder name cannot be empty");
        }
        if (balance < minBal) {
            throw new BankException("Initial balance must be at least minimum balance of " + minBal);
        }
        SavingsAccount acc = new SavingsAccount(name, balance, minBal);
        repo.save(acc);
        processor.registerAccount(acc);
        TransactionLog.append(txLogFile, "OPEN " + acc.getAccountNumber() + " " + balance);
        AuditLogger.logIfAnnotated(this, "openSavingsAccount");
        return acc;
    }

    @Audit(action = "OPEN_ACCOUNT")
    public CurrentAccount openCurrentAccount(String name, long balance, long overdraft) throws BankException {
        if (name == null || name.trim().isEmpty()) {
            throw new BankException("Account holder name cannot be empty");
        }
        if (balance < 0) {
            throw new BankException("Initial balance cannot be negative");
        }
        CurrentAccount acc = new CurrentAccount(name, balance, overdraft);
        repo.save(acc);
        processor.registerAccount(acc);
        TransactionLog.append(txLogFile, "OPEN " + acc.getAccountNumber() + " " + balance);
        AuditLogger.logIfAnnotated(this, "openCurrentAccount");
        return acc;
    }

    @Audit(action = "OPEN_ACCOUNT")
    public FixedDepositAccount openFixedDepositAccount(String name, long balance) throws BankException {
        if (name == null || name.trim().isEmpty()) {
            throw new BankException("Account holder name cannot be empty");
        }
        if (balance <= 0) {
            throw new InvalidAmountException("Fixed deposit initial amount must be positive");
        }
        FixedDepositAccount acc = new FixedDepositAccount(name, balance);
        repo.save(acc);
        processor.registerAccount(acc);
        TransactionLog.append(txLogFile, "OPEN " + acc.getAccountNumber() + " " + balance);
        AuditLogger.logIfAnnotated(this, "openFixedDepositAccount");
        return acc;
    }

    @Audit(action = "DEPOSIT")
    public void deposit(String accNo, long amount) throws BankException {
        Account acc = repo.findById(accNo);
        if (acc == null) {
            throw new AccountNotFoundException("Account " + accNo + " not found");
        }
        acc.deposit(amount);
        TransactionLog.append(txLogFile, "DEPOSIT " + accNo + " " + amount);
        AuditLogger.logIfAnnotated(this, "deposit");
    }

    @Audit(action = "WITHDRAW")
    public void withdraw(String accNo, long amount) throws BankException {
        Account acc = repo.findById(accNo);
        if (acc == null) {
            throw new AccountNotFoundException("Account " + accNo + " not found");
        }
        acc.withdraw(amount);
        TransactionLog.append(txLogFile, "WITHDRAW " + accNo + " " + amount);
        AuditLogger.logIfAnnotated(this, "withdraw");
    }

    @Audit(action = "TRANSFER")
    public void transfer(String fromAccNo, String toAccNo, long amount) throws BankException {
        if (fromAccNo.equalsIgnoreCase(toAccNo)) {
            throw new BankException("Cannot transfer to the same account");
        }
        Account fromAcc = repo.findById(fromAccNo);
        if (fromAcc == null) {
            throw new AccountNotFoundException("Source account " + fromAccNo + " not found");
        }
        Account toAcc = repo.findById(toAccNo);
        if (toAcc == null) {
            throw new AccountNotFoundException("Destination account " + toAccNo + " not found");
        }

        TransactionProcessor.transferSafe(fromAcc, toAcc, amount);
        TransactionLog.append(txLogFile, "TRANSFER " + fromAccNo + " " + amount + " -> " + toAccNo);
        AuditLogger.logIfAnnotated(this, "transfer");
    }

    public String statement(String accNo) throws BankException {
        Account acc = repo.findById(accNo);
        if (acc == null) {
            throw new AccountNotFoundException("Account " + accNo + " not found");
        }
        return StatementFormatter.buildStatement(acc);
    }

    public ArrayList<Account> listAccounts() {
        return repo.findAll();
    }

    public Account findAccount(String accNo) {
        return repo.findById(accNo);
    }

    public void runBatchDemo() {
        Account demoAcc = new SavingsAccount("BatchTest", 1000, 500);
        repo.save(demoAcc);
        processor.registerAccount(demoAcc);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new AccountWorker(demoAcc, 100, 2), "WorkerThread-" + (i + 1));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Batch execution finished. Demo account balance: Rs. " + demoAcc.getBalance());
        TransactionLog.append(txLogFile, "DEPOSIT " + demoAcc.getAccountNumber() + " 1000");
    }

    public void generateReport() {
        ReportGenerator.generateReport(logsDir, reportFile);
    }

    public void saveData() {
        ArrayList<Account> list = repo.findAll();
        StatePersister.saveList(list, dataFile);
    }

    public void loadData() {
        List<Account> loaded = StatePersister.loadList(dataFile);
        for (Account a : loaded) {
            repo.save(a);
            processor.registerAccount(a);
        }
    }

    public void shutdown() {
        saveData();
        processor.stop();
    }
}
