package practical04;

public class StatementFormatter {
    public static String buildStatement(Account acc) {
        if (acc == null) return "No account data.";
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("           ACCOUNT STATEMENT            \n");
        sb.append("========================================\n");
        sb.append("Account Number : ").append(acc.getAccountNumber()).append("\n");
        sb.append("Account Holder : ").append(acc.getOwnerName()).append("\n");
        sb.append("Current Balance: Rs. ").append(acc.getBalance()).append("\n");
        sb.append("Status         : ").append(acc.isActive() ? "Active" : "Inactive").append("\n");
        sb.append("========================================\n");
        return sb.toString();
    }
}
