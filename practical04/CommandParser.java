package practical04;

public class CommandParser {
    public static Command parse(String line) {
        if (line == null) {
            return null;
        }
        String[] tokens = line.trim().split("\\s+");
        if (tokens.length != 3) {
            System.out.println("Error: command must have exactly 3 parts (TYPE ACC_NO AMOUNT)");
            return null;
        }
        try {
            TransactionType type = TransactionType.valueOf(tokens[0].toUpperCase());
            String accNo = tokens[1];
            long amt = Long.parseLong(tokens[2]);
            return new Command(type, accNo, amt);
        } catch (Exception e) {
            System.out.println("Error parsing command: " + e.getMessage());
            return null;
        }
    }
}
