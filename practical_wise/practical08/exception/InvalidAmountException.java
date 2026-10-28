package practical08.exception;

public class InvalidAmountException extends BankException {
    public InvalidAmountException(String msg) {
        super(msg);
    }
}