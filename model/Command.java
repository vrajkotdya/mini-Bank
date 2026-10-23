package model;

public record Command(TransactionType type, String accountNumber, long amount) {
}
