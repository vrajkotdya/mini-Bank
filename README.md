# MiniBank - CEUE203 Object-Oriented Programming

## Project Overview
This repository contains the complete implementation of **MiniBank** developed across Practicals 1 to 13 according to the CEUE203 practical curriculum.

The project is structured into two clean dedicated sections:
1. **`main_application/`**: The complete, production-grade console banking application with full layered architecture (`model`, `service`, `util`, `exception`, `model.annotation`) and the interactive application `MiniBank.java`.
2. **`practical_wise/`**: Step-by-step milestone folders (`practical01` through `practical13`) showing how MiniBank evolved lab by lab.

---

## Folder Structure

```
mini bank/
├── main_application/              # Full Production MiniBank Application
│   ├── model/                     # Data entities, records, enums, interfaces
│   │   ├── Account.java
│   │   ├── SavingsAccount.java
│   │   ├── CurrentAccount.java
│   │   ├── FixedDepositAccount.java
│   │   ├── Customer.java
│   │   ├── BankInfo.java
│   │   ├── MenuOption.java
│   │   ├── TransactionType.java
│   │   ├── Command.java
│   │   ├── Transactable.java
│   │   ├── InterestBearing.java
│   │   ├── WithdrawRule.java
│   │   ├── Premium.java
│   │   ├── AccountWorker.java
│   │   └── AccountStore.java
│   ├── model/annotation/          # Custom reflection annotations (@Id, @Positive, @MaxLength, @Audit)
│   ├── exception/                 # Custom checked exceptions (BankException, InsufficientFundsException, etc.)
│   ├── util/                      # Validators (regex), CommandParser, StatementFormatter, AnnotationValidator
│   ├── service/                   # BankService, Repository<T>, TransactionProcessor, StatePersister, ReportGenerator
│   ├── MiniBank.java              # Main interactive console application
│   └── MiniBankTest.java          # Verification test suite
│
├── practical_wise/                # Individual Practical Folders (1 to 13)
│   ├── practical01/               # Practical 1: Menu shell, BankInfo record, MenuOption enum
│   ├── practical02/               # Practical 2: Customer, Account, encapsulation, ID generation
│   ├── practical03/               # Practical 3: toString, equals, hashCode, nested Address, Cloneable
│   ├── practical04/               # Practical 4: Regex validation, CommandParser, StatementFormatter
│   ├── practical05/               # Practical 5: Abstract Account, Savings/Current/FixedDeposit hierarchy
│   ├── practical06/               # Practical 6: Interfaces, lambda, anonymous classes, packages, static import
│   ├── practical07/               # Practical 7: Custom annotations (@Id, @Positive, @MaxLength), reflection
│   ├── practical08/               # Practical 8: Custom exception hierarchy, try-catch-finally, AutoCloseable
│   ├── practical09/               # Practical 9: Multithreading, Runnable, synchronized thread safety
│   ├── practical10/               # Practical 10: ExecutorService thread pool, producer-consumer, deadlock fix
│   ├── practical11/               # Practical 11: Serialization (accounts.dat), transaction logs, NIO report
│   ├── practical12/               # Practical 12: Collections (HashMap, ArrayList, Comparator, ConcurrentHashMap)
│   └── practical13/               # Practical 13: Generic Repository<T> (minimal & clean)
│
├── PROJECT_EXPLANATION.md         # Detailed technical & non-technical project guide
├── README.md                      # Quick overview & run guide
└── test_all.ps1                   # Automated test script for all practicals & main app
```

---

## How to Compile and Run

### 1. Run Main Application
```bash
cd main_application
javac exception/*.java model/annotation/*.java model/*.java util/*.java service/*.java MiniBank.java MiniBankTest.java
java MiniBank
```

### 2. Run Main Application Tests
```bash
cd main_application
java MiniBankTest
```

### 3. Run Any Individual Practical (e.g., Practical 1 or Practical 13)
```bash
cd practical_wise/practical01
javac *.java
java practical01.MiniBank
```
```bash
cd practical_wise/practical13
javac *.java
java practical13.MiniBank
```
