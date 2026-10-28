package practical11;

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
            System.out.println("No logs directory found.");
            return;
        }

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int fileCount = 0;
        StringBuilder sb = new StringBuilder();

        sb.append("--- TRANSACTION REPORT ---\n");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir, "*.log")) {
            for (Path entry : stream) {
                fileCount++;
                BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                sb.append("File: ").append(entry.getFileName())
                  .append(" | Size: ").append(attrs.size()).append(" bytes\n");

                try (BufferedReader reader = Files.newBufferedReader(entry)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");
                        if (parts.length >= 3) {
                            String type = parts[0].toUpperCase();
                            try {
                                long amt = Long.parseLong(parts[2]);
                                if (type.equals("DEPOSIT")) {
                                    totalDeposits += amt;
                                } else if (type.equals("WITHDRAW")) {
                                    totalWithdrawals += amt;
                                }
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading logs: " + e.getMessage());
            return;
        }

        sb.append("Total Files: ").append(fileCount).append("\n");
        sb.append("Total Deposits: Rs. ").append(totalDeposits).append("\n");
        sb.append("Total Withdrawals: Rs. ").append(totalWithdrawals).append("\n");
        sb.append("Net Change: Rs. ").append(totalDeposits - totalWithdrawals).append("\n");

        System.out.println(sb.toString());

        try {
            if (reportFile.getParent() != null && !Files.exists(reportFile.getParent())) {
                Files.createDirectories(reportFile.getParent());
            }
            Files.write(reportFile, Collections.singletonList(sb.toString()));
            System.out.println("Summary written to: " + reportFile);
        } catch (IOException e) {
            System.out.println("Write report error: " + e.getMessage());
        }
    }
}