package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

public class ReportGenerator {
    public static void generateReport(Path logsDir, Path reportFile) {
        if (!Files.exists(logsDir)) {
            System.out.println("Logs folder does not exist: " + logsDir);
            return;
        }

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int fileCount = 0;
        StringBuilder sb = new StringBuilder();

        sb.append("========================================\n");
        sb.append("       MINIBANK TRANSACTION REPORT      \n");
        sb.append("========================================\n");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir, "*.log")) {
            for (Path entry : stream) {
                fileCount++;
                BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                sb.append("Log File: ").append(entry.getFileName())
                  .append(" (Size: ").append(attrs.size()).append(" bytes, Modified: ")
                  .append(attrs.lastModifiedTime()).append(")\n");

                try (BufferedReader reader = Files.newBufferedReader(entry)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String trimmed = line.trim();
                        if (trimmed.isEmpty()) continue;
                        String[] parts = trimmed.split("\\s+");
                        if (parts.length >= 3) {
                            String type = parts[0].toUpperCase();
                            try {
                                long amt = Long.parseLong(parts[2]);
                                if (type.contains("DEPOSIT")) {
                                    totalDeposits += amt;
                                } else if (type.contains("WITHDRAW")) {
                                    totalWithdrawals += amt;
                                }
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
            return;
        }

        long netChange = totalDeposits - totalWithdrawals;
        sb.append("----------------------------------------\n");
        sb.append("Files Processed   : ").append(fileCount).append("\n");
        sb.append("Total Deposits    : Rs. ").append(totalDeposits).append("\n");
        sb.append("Total Withdrawals : Rs. ").append(totalWithdrawals).append("\n");
        sb.append("Net Cashflow      : Rs. ").append(netChange).append("\n");
        sb.append("========================================\n");

        String reportText = sb.toString();
        System.out.println(reportText);

        try {
            if (reportFile.getParent() != null && !Files.exists(reportFile.getParent())) {
                Files.createDirectories(reportFile.getParent());
            }
            Files.write(reportFile, Collections.singletonList(reportText));
            System.out.println("Report saved to " + reportFile);
        } catch (IOException e) {
            System.out.println("Could not save report file: " + e.getMessage());
        }
    }
}
