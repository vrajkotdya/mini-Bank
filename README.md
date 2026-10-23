# MiniBank - CEUE203 Object-Oriented Programming

## Project Overview
This repository contains the complete implementation of **MiniBank** developed across Practicals 1 to 13 according to the CEUE203 practical curriculum.

The project is structured in two ways:
1. **Full Integrated MiniBank Application**: Built with a clean layered architecture using packages (`model`, `service`, `util`, `exception`, `model.annotation`) and the main runnable console app `MiniBank.java`.
2. **Individual Practical Folders (`practical01` to `practical13`)**: Each folder contains the exact milestone code corresponding to Part B of each practical lab.

---

## Folder Structure

```
mini bank/
├── model/                  # Data models, records, enums, account types, interfaces
│   ├── Account.java
│   ├── SavingsAccount.java
│   ├── CurrentAccount.java
│   ├── FixedDepositAccount.java
│   ├── Customer.java (with nested Address)
│   ├── BankInfo.java (record)
│   ├── MenuOption.java (enum)
│   ├── TransactionType.java (enum)
│   ├── Command.java (record)
│   ├── Transactable.java (interface)
│   ├── InterestBearing.java (interface with default methods)
│   ├── WithdrawRule.java (functional interface)
│   ├── Premium.java (marker interface)
│   ├── AccountWorker.java (multithreading runnable)
│   └── AccountStore.java (collections & sorting)
├── model/annotation/       # Custom reflection annotations
│   ├── Id.java
│   ├── Positive.java
│   ├── MaxLength.java
│   └── Audit.java
├── exception/              # Custom checked exception hierarchy
│   ├── BankException.java
│   ├── InsufficientFundsException.java
│   ├── AccountNotFoundException.java
│   ├── InvalidAmountException.java
│   └── DailyLimitExceededException.java
├── util/                   # Parsers, formatters, and validators
│   ├── Validator.java (regex for mobile, email, PAN, IFSC, amount)
│   ├── CommandParser.java (command line tokenization)
│   ├── StatementFormatter.java (StringBuilder statements)
│   ├── AnnotationValidator.java (runtime reflection validation)
│   ├── BankSession.java (AutoCloseable try-with-resources)
│   └── AuditLogger.java (reflective method logging)
├── service/                # Business logic, persistence, and concurrency
│   ├── BankService.java (facade for banking operations)
│   ├── Repository.java (generic type-safe repository with @Id reflection)
│   ├── TransactionProcessor.java (thread pool, producer-consumer, deadlock-safe transfer)
│   ├── StatePersister.java (object serialization/deserialization)
│   ├── TransactionLog.java (append-only NIO log writer)
│   └── ReportGenerator.java (NIO directory streams and attributes)
├── MiniBank.java           # Main menu-driven interactive application
├── MiniBankTest.java       # Verification test suite for all milestones
├── practical01/            # Practical 1: Menu shell, BankInfo record, MenuOption enum
├── practical02/            # Practical 2: Customer, Account, encapsulation, ID generation
├── practical03/            # Practical 3: toString, equals, hashCode, nested Address, Cloneable
├── practical04/            # Practical 4: Regex validation, CommandParser, StatementFormatter
├── practical05/            # Practical 5: Abstract Account, Savings/Current/FixedDeposit hierarchy
├── practical06/            # Practical 6: Interfaces, lambda, anonymous classes, packages, static import
├── practical07/            # Practical 7: Custom annotations (@Id, @Positive, @MaxLength), reflection
├── practical08/            # Practical 8: Custom exception hierarchy, try-catch-finally, AutoCloseable
├── practical09/            # Practical 9: Multithreading, Runnable, synchronized thread safety
├── practical10/            # Practical 10: ExecutorService thread pool, producer-consumer, deadlock fix
├── practical11/            # Practical 11: Serialization (accounts.dat), transaction logs, NIO report
├── practical12/            # Practical 12: Collections (HashMap, ArrayList, Comparator, ConcurrentHashMap)
└── practical13/            # Practical 13: Generic Repository<T> simple storage
```

---

## How to Compile and Run

### 1. Compile Full MiniBank Project
```bash
javac exception/*.java model/annotation/*.java model/*.java util/*.java service/*.java MiniBank.java MiniBankTest.java
```

### 2. Run Main Interactive Application
```bash
java MiniBank
```

### 3. Run Verification Tests
```bash
java MiniBankTest
```

### 4. Compile and Run Any Individual Practical (e.g., Practical 1 or Practical 13)
```bash
javac practical01/*.java
java practical01.MiniBank
```
```bash
javac practical13/*.java
java practical13.MiniBank
```
