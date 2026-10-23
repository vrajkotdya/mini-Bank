package util;

import model.Account;

public class StatementFormatter {
    public static String buildStatement(Account acc) {
        if (acc == null) return "No account data available.";
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("           MINIBANK STATEMENT           \n");
        sb.append("========================================\n");
        sb.append("Account Number : ").append(acc.getAccountNumber()).append("\n");
        sb.append("Account Holder : ").append(acc.getOwnerName()).append("\n");
        sb.append("Account Type   : ").append(acc.getClass().getSimpleName()).append("\n");
        sb.append("Interest Rate  : ").append(acc.interestRate()).append("%\n");
        sb.append("Balance        : Rs. ").append(acc.getBalance()).append("\n");
        sb.append("Status         : ").append(acc.isActive() ? "Active" : "Inactive").append("\n");
        sb.append("========================================\n");
        return sb.toString();
    }
}
