package exception;

public class InsufficientFundsException extends BankException {
    private long shortfall;

    public InsufficientFundsException(String msg, long shortfall) {
        super(msg);
        this.shortfall = shortfall;
    }

    public long getShortfall() {
        return shortfall;
    }
}
