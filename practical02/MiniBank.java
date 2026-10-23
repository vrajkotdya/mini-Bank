package practical02;

public class MiniBank {
    public static void main(String[] args) {
        Customer c1 = new Customer("Riya", "riya@gmail.com", "9876543210");
        System.out.println("Customer ID: " + c1.getCustomerId() + " Name: " + c1.getName());

        Account[] accounts = new Account[3];
        accounts[0] = new Account("Riya", 5000);
        accounts[1] = new Account("Aman", 3000);
        accounts[2] = new Account("Pooja");

        accounts[0].deposit(2000);
        System.out.println(accounts[0].getOwnerName() + " Balance after deposit 2000: " + accounts[0].getBalance());

        boolean ok1 = accounts[0].withdraw(3000);
        System.out.println("Withdraw 3000 status: " + ok1 + ", Balance: " + accounts[0].getBalance());

        boolean ok2 = accounts[0].withdraw(10000);
        System.out.println("Withdraw 10000 status: " + ok2 + ", Balance: " + accounts[0].getBalance());

        boolean tr = accounts[0].transfer(accounts[1], 1000);
        System.out.println("Transfer 1000 status: " + tr);

        for (int i = 0; i < accounts.length; i++) {
            System.out.println("Account " + accounts[i].getAccountNumber() + " (" + accounts[i].getOwnerName() + ") -> Rs. " + accounts[i].getBalance());
        }
    }
}
