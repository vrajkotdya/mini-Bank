package practical03;

public class MiniBank {
    public static void main(String[] args) {
        Customer.Address addr = new Customer.Address("123 MG Road", "Anand", "388001");
        Customer c1 = new Customer("Riya", "riya@gmail.com", "9876543210", addr);
        Customer c2 = c1.clone();

        System.out.println("Customer 1: " + c1);
        System.out.println("Customer 2 (clone): " + c2);
        System.out.println("Cloned address check: " + (c1.getAddress() != c2.getAddress() && c1.getAddress().getCity().equals(c2.getAddress().getCity())));

        Account a1 = new Account("AC0001", "Riya", 4000);
        Account a2 = new Account("AC0001", "Riya Patel", 9000);
        Account a3 = new Account("AC0002", "Aman", 2500);

        System.out.println("\nAccount a1: " + a1);
        System.out.println("a1.equals(a2): " + a1.equals(a2));
        System.out.println("a1.equals(a3): " + a1.equals(a3));

        Object obj = a1;
        if (obj instanceof Account) {
            Account acc = (Account) obj;
            System.out.println("Instanceof check passed for: " + acc.getAccountNumber());
        }
    }
}
