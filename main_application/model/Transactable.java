package model;

import exception.BankException;

public interface Transactable {
    void deposit(long amount) throws BankException;
    boolean withdraw(long amount) throws BankException;
}
