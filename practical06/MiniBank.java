package practical06;

import static practical06.util.Validator.*;
import practical06.model.Account;
import practical06.model.InterestBearing;
import practical06.model.Premium;
import practical06.model.SavingsAccount;
import practical06.model.WithdrawRule;

public class MiniBank {
    public static void main(String[] args) {
        SavingsAccount sa = new SavingsAccount("AC0001", "Riya", 10000, 1000);

        System.out.println("Account: " + sa);
        System.out.println("Yearly Interest: Rs. " + sa.yearlyInterest());
        System.out.println("Monthly Interest: Rs. " + sa.monthlyInterest());
        System.out.println("Is Premium account: " + (sa instanceof Premium));

        WithdrawRule anonRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return (account.getBalance() - amount) >= 1000;
            }
        };

        WithdrawRule lambdaRule = (account, amount) -> (account.getBalance() - amount) >= 1000;

        System.out.println("Anonymous class allow 5000: " + anonRule.allow(sa, 5000));
        System.out.println("Lambda expression allow 5000: " + lambdaRule.allow(sa, 5000));
        System.out.println("Lambda expression allow 9500: " + lambdaRule.allow(sa, 9500));

        System.out.println("Mobile check via static import: " + isValidMobile("9876543210"));
    }
}
