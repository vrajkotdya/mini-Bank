package practical06.model;

public interface Transactable {
    void deposit(long amount);
    boolean withdraw(long amount);
}
