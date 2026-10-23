package model;

@FunctionalInterface
public interface WithdrawRule {
    boolean allow(Account account, long amount);
}
