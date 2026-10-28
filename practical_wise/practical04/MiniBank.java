package practical04;

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("Testing Validators:");
        System.out.println("Mobile 9876543210: " + Validator.isValidMobile("9876543210"));
        System.out.println("Mobile 12345: " + Validator.isValidMobile("12345"));

        System.out.println("Email abc@xyz.com: " + Validator.isValidEmail("abc@xyz.com"));
        System.out.println("Email invalid-email: " + Validator.isValidEmail("invalid-email"));

        System.out.println("PAN ABCDE1234F: " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN 12345: " + Validator.isValidPan("12345"));

        System.out.println("IFSC SBIN0001234: " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC 1234: " + Validator.isValidIfsc("1234"));

        System.out.println("\nTesting Command Parser:");
        String cmdStr = "DEPOSIT AC0001 500";
        Command cmd = CommandParser.parse(cmdStr);
        if (cmd != null) {
            System.out.println("Parsed Command: Type=" + cmd.type() + ", Account=" + cmd.accountNumber() + ", Amount=" + cmd.amount());
        }

        Account acc = new Account("AC0001", "Riya", 5000);
        acc.deposit(cmd.amount());

        System.out.println("\n" + StatementFormatter.buildStatement(acc));
    }
}
