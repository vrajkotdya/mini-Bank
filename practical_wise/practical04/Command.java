package practical04;

public record Command(TransactionType type, String accountNumber, long amount) {
}
