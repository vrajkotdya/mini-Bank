package exception;

public class InvalidAmountException extends BankException {
    public InvalidAmountException(String msg) {
        super(msg);
    }
}
