import java.util.ArrayList;
import java.util.Scanner;
import static util.Validator.*;
import exception.BankException;
import model.Account;
import model.BankInfo;
import service.BankService;

public class MiniBank {
    public static void main(String[] args) {
        BankService service = new BankService();
        BankInfo info = service.getBankInfo();

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("========================================");
        System.out.println("   Welcome to " + info.name() + " (" + info.branch() + ")");
        System.out.println("========================================");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Account Statement");
            System.out.println("6. List All Accounts");
            System.out.println("7. Run Batch Processing Engine");
            System.out.println("8. Generate Audit Report");
            System.out.println("9. Bank Info & Working Hours");
            System.out.println("10. Exit");
            System.out.print("Enter choice (1-10): ");

            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid number. Please try again.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("\nSelect Account Type:");
                    System.out.println("1. Savings Account");
                    System.out.println("2. Current Account");
                    System.out.println("3. Fixed Deposit Account");
                    System.out.print("Enter type: ");
                    int type = Integer.parseInt(sc.nextLine().trim());

                    System.out.print("Enter Account Holder Name: ");
                    String name = sc.nextLine().trim();

                    System.out.print("Enter Initial Deposit: ");
                    long initBal = Long.parseLong(sc.nextLine().trim());

                    try {
                        Account newAcc = switch (type) {
                            case 1 -> {
                                System.out.print("Enter Minimum Balance: ");
                                long minBal = Long.parseLong(sc.nextLine().trim());
                                yield service.openSavingsAccount(name, initBal, minBal);
                            }
                            case 2 -> {
                                System.out.print("Enter Overdraft Limit: ");
                                long od = Long.parseLong(sc.nextLine().trim());
                                yield service.openCurrentAccount(name, initBal, od);
                            }
                            case 3 -> service.openFixedDepositAccount(name, initBal);
                            default -> throw new BankException("Unknown account type selected");
                        };
                        System.out.println("Account created successfully! Account Number: " + newAcc.getAccountNumber());
                    } catch (BankException e) {
                        System.out.println("Failed to create account: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.print("Enter Account Number: ");
                    String accNo = sc.nextLine().trim();
                    System.out.print("Enter Amount to Deposit: ");
                    String amtStr = sc.nextLine().trim();
                    if (!isValidAmount(amtStr)) {
                        System.out.println("Invalid amount entered.");
                        break;
                    }
                    long amt = Long.parseLong(amtStr);
                    try {
                        service.deposit(accNo, amt);
                        System.out.println("Deposit successful! New Balance: Rs. " + service.findAccount(accNo).getBalance());
                    } catch (BankException e) {
                        System.out.println("Deposit failed: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.print("Enter Account Number: ");
                    String accNo = sc.nextLine().trim();
                    System.out.print("Enter Amount to Withdraw: ");
                    String amtStr = sc.nextLine().trim();
                    if (!isValidAmount(amtStr)) {
                        System.out.println("Invalid amount entered.");
                        break;
                    }
                    long amt = Long.parseLong(amtStr);
                    try {
                        service.withdraw(accNo, amt);
                        System.out.println("Withdrawal successful! Remaining Balance: Rs. " + service.findAccount(accNo).getBalance());
                    } catch (BankException e) {
                        System.out.println("Withdrawal failed: " + e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.print("Enter Source Account Number: ");
                    String fromAcc = sc.nextLine().trim();
                    System.out.print("Enter Destination Account Number: ");
                    String toAcc = sc.nextLine().trim();
                    System.out.print("Enter Amount to Transfer: ");
                    String amtStr = sc.nextLine().trim();
                    if (!isValidAmount(amtStr)) {
                        System.out.println("Invalid amount entered.");
                        break;
                    }
                    long amt = Long.parseLong(amtStr);
                    try {
                        service.transfer(fromAcc, toAcc, amt);
                        System.out.println("Transfer successful!");
                    } catch (BankException e) {
                        System.out.println("Transfer failed: " + e.getMessage());
                    }
                }
                case 5 -> {
                    System.out.print("Enter Account Number: ");
                    String accNo = sc.nextLine().trim();
                    try {
                        String st = service.statement(accNo);
                        System.out.println(st);
                    } catch (BankException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 6 -> {
                    ArrayList<Account> list = service.listAccounts();
                    if (list.isEmpty()) {
                        System.out.println("No accounts registered yet.");
                    } else {
                        System.out.println("\nAll Accounts (" + list.size() + "):");
                        for (Account a : list) {
                            System.out.println(" - " + a);
                        }
                    }
                }
                case 7 -> {
                    System.out.println("Launching multithreaded batch processing...");
                    service.runBatchDemo();
                }
                case 8 -> {
                    System.out.println("Generating transaction report using NIO...");
                    service.generateReport();
                }
                case 9 -> {
                    System.out.println("Bank: " + info.name());
                    System.out.println("Branch: " + info.branch());
                    System.out.println("Working Hours: Monday to Friday, 9:30 AM - 4:30 PM");
                    System.out.println("Helpline: 1800-123-4567");
                }
                case 10 -> {
                    System.out.println("Saving bank records...");
                    service.shutdown();
                    System.out.println("Thank you for banking with us. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid selection. Choose between 1 and 10.");
            }
        }
        sc.close();
    }
}
