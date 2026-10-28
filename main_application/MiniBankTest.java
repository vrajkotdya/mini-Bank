import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import exception.BankException;
import exception.InsufficientFundsException;
import model.Account;
import model.AccountWorker;
import model.Customer;
import model.SavingsAccount;
import service.BankService;
import service.Repository;
import service.StatePersister;
import util.AnnotationValidator;
import util.Validator;

public class MiniBankTest {
    private static int passed = 0;
    private static int total = 0;

    private static void check(String name, boolean condition) {
        total++;
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        System.out.println("Running MiniBank Verification Tests...\n");

        testDepositIncreasesBalance();
        testWithdrawOverLimitThrows();
        testConcurrentDeposits();
        testAnnotationValidation();
        testGenericRepository();
        testPersistence();
        testStringValidators();

        System.out.println("\nTest Summary: " + passed + " / " + total + " tests passed.");
    }

    public static void testDepositIncreasesBalance() {
        try {
            SavingsAccount acc = new SavingsAccount("Riya", 5000, 1000);
            acc.deposit(2000);
            check("Deposit increases balance", acc.getBalance() == 7000);
        } catch (Exception e) {
            check("Deposit increases balance", false);
        }
    }

    public static void testWithdrawOverLimitThrows() {
        SavingsAccount acc = new SavingsAccount("Aman", 3000, 1000);
        boolean caught = false;
        try {
            acc.withdraw(2500);
        } catch (InsufficientFundsException e) {
            caught = true;
        } catch (Exception e) {
        }
        check("Withdrawal over limit throws InsufficientFundsException", caught);
    }

    public static void testConcurrentDeposits() {
        try {
            SavingsAccount acc = new SavingsAccount("ThreadTest", 0, 0);
            List<Thread> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Thread t = new Thread(new AccountWorker(acc, 1000, 1));
                list.add(t);
                t.start();
            }
            for (Thread t : list) {
                t.join();
            }
            check("Concurrent deposits thread safety (10x1000 = 10000)", acc.getBalance() == 10000);
        } catch (Exception e) {
            check("Concurrent deposits thread safety", false);
        }
    }

    public static void testAnnotationValidation() {
        SavingsAccount invalidAcc = new SavingsAccount("Invalid", -100, 0);
        String[] errs = AnnotationValidator.validate(invalidAcc);
        check("Annotation validator flags negative balance", errs.length > 0);
    }

    public static void testGenericRepository() {
        Repository<Customer> custRepo = new Repository<>();
        Customer c1 = new Customer("John", "john@gmail.com", "9876543210");
        custRepo.save(c1);
        Customer fetched = custRepo.findById(c1.getCustomerId());
        check("Generic Repository saves and retrieves Customer by @Id", fetched != null && fetched.getName().equals("John"));

        Double[] vals = { 10.0, 20.0, 30.0 };
        double avg = Repository.average(vals);
        check("Generic Repository bounded average calculation", avg == 20.0);
    }

    public static void testPersistence() {
        try {
            SavingsAccount a1 = new SavingsAccount("TestUser", 4500, 500);
            Account[] arr = new Account[] { a1 };
            java.nio.file.Path tmpPath = Paths.get("data", "test_accounts.dat");
            StatePersister.save(arr, tmpPath);
            Account[] loaded = StatePersister.load(tmpPath);
            check("Persistence saves and reloads accounts correctly", loaded.length == 1 && loaded[0].getBalance() == 4500);
        } catch (Exception e) {
            check("Persistence saves and reloads accounts correctly", false);
        }
    }

    public static void testStringValidators() {
        boolean v1 = Validator.isValidMobile("9876543210");
        boolean v2 = !Validator.isValidMobile("12345");
        boolean v3 = Validator.isValidEmail("test@bank.com");
        boolean v4 = Validator.isValidPan("ABCDE1234F");
        boolean v5 = Validator.isValidIfsc("SBIN0001234");
        check("Regex validation tests", v1 && v2 && v3 && v4 && v5);
    }
}
