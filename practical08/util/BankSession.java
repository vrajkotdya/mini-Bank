package practical08.util;

public class BankSession implements AutoCloseable {
    private String id;

    public BankSession(String id) {
        this.id = id;
        System.out.println("AutoCloseable BankSession opened: " + id);
    }

    @Override
    public void close() {
        System.out.println("AutoCloseable BankSession safely closed: " + id);
    }
}
