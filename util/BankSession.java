package util;

public class BankSession implements AutoCloseable {
    private String sessionId;

    public BankSession(String sessionId) {
        this.sessionId = sessionId;
        System.out.println("Session opened: " + sessionId);
    }

    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void close() {
        System.out.println("Session closed: " + sessionId);
    }
}
