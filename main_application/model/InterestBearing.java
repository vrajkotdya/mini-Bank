package model;

public interface InterestBearing {
    double interestRate();
    long getBalance();

    default double yearlyInterest() {
        return (getBalance() * interestRate()) / 100.0;
    }

    default double monthlyInterest() {
        return yearlyInterest() / 12.0;
    }
}
