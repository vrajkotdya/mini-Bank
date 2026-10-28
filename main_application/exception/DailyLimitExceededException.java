package exception;

public class DailyLimitExceededException extends BankException {
    public DailyLimitExceededException(String msg) {
        super(msg);
    }
}
