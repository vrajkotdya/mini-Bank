package practical13;

public class MiniBank {
    public static void main(String[] args) {
        Repository<Account> repo = new Repository<>();
        repo.save("AC0001", new Account("AC0001", "Riya", 5000));
        repo.save("AC0002", new Account("AC0002", "Aman", 8000));

        System.out.println("Find AC0001: " + repo.findById("AC0001"));
        System.out.println("All accounts: " + repo.findAll());
    }
}