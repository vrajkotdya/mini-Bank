package practical08.exception;

public class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String msg) {
        super(msg);
    }
}