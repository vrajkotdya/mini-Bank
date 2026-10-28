# MiniBank — Complete Project Guide & Technical Walkthrough

Welcome to the comprehensive guide for **MiniBank**, a full-featured console banking application developed in Java. 

This document explains everything about the system: how it works under the hood, why specific programming techniques were chosen, and what each part does. Whether you are a software engineer wanting technical details or a non-technical reader curious about the mechanics of modern software, this guide breaks it down with clear explanations and real-world analogies.

---

## 1. What is MiniBank? (Executive Summary)

**MiniBank** is a simulation of a core banking system. Just like a real bank (such as SBI, HDFC, or Bank of America), MiniBank handles everyday financial operations:
- Opening different types of accounts (Savings, Current, Fixed Deposit).
- Depositing and withdrawing funds safely.
- Transferring money between accounts.
- Producing account statements.
- Handling multiple transactions at the exact same second without errors.
- Saving all records permanently to disk so no data is lost when the program closes.

### The Non-Technical Analogy:
> Imagine a physical bank branch with a customer counter, an account filing cabinet, an automated ATM room, and an accountant in the back office. 
> - **MiniBank** is the complete software brain that runs that entire branch — ensuring money doesn't disappear, calculations are accurate, and rules are followed.

---

## 2. High-Level System Architecture

MiniBank is structured into clean, separated **layers**:

```
+-------------------------------------------------------------+
|                     1. User Interface                       |
|         MiniBank.java — Interactive Console Menu            |
|       (Accepts choices: 1-Open, 2-Deposit, 3-Withdraw, etc.) |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                  2. Security & Validation                   |
|      Validator.java, CommandParser.java, Annotations        |
|  (Verifies phone numbers, emails, positive amounts, rules)   |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                  3. Business Logic Service                  |
|                      BankService.java                       |
|    (Coordinates account creation, transfers, interest calc) |
+-------------------------------------------------------------+
               |                              |
               v                              v
+-----------------------------+   +---------------------------+
|    4. Concurrency Engine    |   |     5. Storage & Data     |
| TransactionProcessor.java   |   |   Repository.java         |
|  - 4-Thread Pool            |   |   - Memory Map (Fast)     |
|  - Producer-Consumer Queue  |   |   StatePersister.java     |
|  - Deadlock-Safe Locks      |   |   - Disk Snapshot (.dat)  |
|                             |   |   TransactionLog.java     |
|                             |   |   - Audit Log Files (.log)|
+-----------------------------+   +---------------------------+
```

---

## 3. Step-by-Step Lab Progression (Practicals 1 to 13)

MiniBank was built incrementally across 13 progressive milestones:

### Practical 1: Setting up the Storefront (Menu & Basic Types)
- **What it does**: Displays the opening welcome screen and the numbered options menu (1 to 5). Reads user input.
- **Technical Highlights**: `BankInfo` record, `MenuOption` enum, Java switch expressions.
- **Non-Technical Explanation**: Like printing a clean, numbered service menu for visiting bank customers.

### Practical 2: Customers and Accounts (Encapsulation)
- **What it does**: Introduces `Customer` and `Account`. Auto-generates IDs (`CUST101`, `AC0001`). Prevents tampering with balances.
- **Technical Highlights**: `private` balance field, getter-only access, static ID counters.
- **Non-Technical Explanation**: Money is kept inside a vault; customers cannot rewrite their balance with a pencil.

### Practical 3: Object Identity, Comparison & Addresses
- **What it does**: Enables clean text representation (`toString()`) and comparison (`equals()`, `hashCode()`). Adds nested `Address` and cloning.
- **Technical Highlights**: Identity by account number, static nested class, `Cloneable`.
- **Non-Technical Explanation**: If two passbooks have the same account number, they point to the exact same account.

### Practical 4: Input Validation & Command Parsing
- **What it does**: Validates mobile numbers, emails, PAN cards, and IFSC codes with regex. Formats statements.
- **Technical Highlights**: `java.util.regex.Pattern`, `StringBuilder`, `CommandParser`.
- **Non-Technical Explanation**: Security guard at the entrance checking that documents are filled correctly before processing.

### Practical 5: Account Types & Polymorphism
- **What it does**: Adds Savings (4% interest, min balance), Current (0% interest, overdraft), and Fixed Deposit (7% interest, locked).
- **Technical Highlights**: Abstract class `Account`, abstract methods, dynamic method dispatch.
- **Non-Technical Explanation**: Different types of accounts follow different withdrawal and interest rules while sharing a common foundation.

### Practical 6: Interfaces, Lambdas & Modular Packaging
- **What it does**: Defines capabilities via interfaces (`Transactable`, `InterestBearing`, `WithdrawRule`, `Premium`). Organizes into packages.
- **Technical Highlights**: Default interface methods, `@FunctionalInterface`, lambda expressions.
- **Non-Technical Explanation**: An interface is a standardized power plug: any device that fits can be used.

### Practical 7: Custom Annotations & Reflection
- **What it does**: Adds metadata tags (`@Id`, `@Positive`, `@MaxLength`) validated at runtime by reflection.
- **Technical Highlights**: Custom runtime annotations, `Field.isAnnotationPresent()`, `Field.get()`.
- **Non-Technical Explanation**: Adding colored inspection stickers on forms that automated scanners verify.

### Practical 8: Custom Exceptions & Fault Tolerance
- **What it does**: Replaces error booleans with custom exceptions (`InsufficientFundsException` carrying exact shortfall).
- **Technical Highlights**: Checked exception hierarchy, try-catch-finally, `AutoCloseable` try-with-resources.
- **Non-Technical Explanation**: The ATM gives you an exact explanation ("Short by Rs. 4,000") and safely closes your session.

### Practical 9: Multithreading & Race Conditions
- **What it does**: Runs 10 concurrent threads depositing money and proves balance corruption without synchronization. Fixes it with `synchronized`.
- **Technical Highlights**: `Runnable`, thread safety, race conditions.
- **Non-Technical Explanation**: Prevents two debit cards swiped at the same instant from overwriting each other's updates.

### Practical 10: Thread Pool, Producer-Consumer & Deadlock Prevention
- **What it does**: ExecutorService thread pool (4 threads), wait/notify queue, and deadlock-safe account locking order.
- **Technical Highlights**: `newFixedThreadPool(4)`, lock ordering by account number.
- **Non-Technical Explanation**: Prevents two cars facing each other on a single-lane bridge from blocking each other forever.

### Practical 11: Data Persistence & Log Auditing (NIO)
- **What it does**: Saves accounts to binary file (`accounts.dat`), writes append-only transaction logs, and compiles audit reports using NIO.
- **Technical Highlights**: `ObjectOutputStream`, `ObjectInputStream`, NIO `DirectoryStream`.
- **Non-Technical Explanation**: Ensures customer accounts survive computer reboots and maintains an unalterable logbook.

### Practical 12: Collections Framework & Sorting
- **What it does**: Upgrades storage to `HashMap<String, Account>`, `ArrayList` history, `Comparator` sorting by balance, and `ConcurrentHashMap`.
- **Technical Highlights**: \(O(1)\) key lookups, Comparator, thread-safe collections.
- **Non-Technical Explanation**: Replacing a fixed bookshelf with a modern digital filing cabinet.

### Practical 13: Generic Repository Pattern (Simple & Minimal)
- **What it does**: Creates a clean, minimal, reusable generic `Repository<T>` storing objects safely by ID in a map.
- **Technical Highlights**: Java Generics `<T>`, `save(id, item)`, `findById(id)`, `findAll()`.
- **Non-Technical Explanation**: A universal container that safely stores and retrieves any record without mixing up types.

---

## 4. Summary Table of Core Technical Concepts

| Concept | Where Used in MiniBank | Real-World Non-Technical Purpose |
|---|---|---|
| **Records** | `BankInfo`, `Command` | Laminated immutable identity cards that cannot be changed once printed |
| **Enums** | `MenuOption`, `TransactionType` | Pre-approved multiple-choice dropdown lists to avoid typos |
| **Encapsulation** | `Account` private balance | Locking cash in a safe so nobody can edit their balance with a pen |
| **Polymorphism** | `SavingsAccount`, `CurrentAccount`, `FixedDepositAccount` | Treating all accounts as "Accounts" while each runs its own interest math |
| **Custom Annotations** | `@Id`, `@Positive`, `@MaxLength` | Sticky notes attached to fields that automated scanner tools read and verify |
| **Custom Exceptions** | `InsufficientFundsException` | Smart alarms that state the exact shortfall rather than a generic error |
| **Synchronization** | `synchronized deposit()` | Locking the teller window so two people can't modify the same account at once |
| **Lock Ordering** | `TransactionProcessor.transferSafe()` | Traffic rule preventing two simultaneous transfers from locking each other in a stalemate |
| **Serialization** | `StatePersister.java` | Freezing memory state into a file so balances survive computer restarts |
| **Generics** | `Repository<T>` | Universal filing cabinet that stores Accounts, Customers, or any other records |

---

## 5. How to Compile and Run

### To run the full MiniBank application:
```powershell
cd main_application
javac exception/*.java model/annotation/*.java model/*.java util/*.java service/*.java MiniBank.java MiniBankTest.java
java MiniBank
```

### To run the automated verification test suite:
```powershell
cd main_application
java MiniBankTest
```

### To run any individual lab milestone (e.g. Practical 1 or Practical 13):
```powershell
# Practical 1
cd practical_wise/practical01
javac *.java
java practical01.MiniBank

# Practical 13
cd practical_wise/practical13
javac *.java
java practical13.MiniBank
```
