package practical07;

import practical07.model.Account;
import practical07.util.AnnotationValidator;

public class MiniBank {
    public static void main(String[] args) {
        Account invalidAcc = new Account("AC0001", "Riya Patel Very Long Name", -100);
        System.out.println("Validating invalid account: " + invalidAcc);

        String[] errors = AnnotationValidator.validate(invalidAcc);
        System.out.println("Validation errors found: " + errors.length);
        for (String err : errors) {
            System.out.println(" - " + err);
        }

        Account validAcc = new Account("AC0002", "Riya", 5000);
        System.out.println("\nValidating valid account: " + validAcc);
        String[] noErrors = AnnotationValidator.validate(validAcc);
        System.out.println("Validation errors found: " + noErrors.length);
    }
}