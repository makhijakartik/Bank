import java.util.Scanner;

class Human {
    protected String name;

    public Human(String name) {
        this.name = name;
    }
}

class Customer extends Human {
    private int accountNumber;
    private double balance;
    private static String bankName; // Static bank name

    // Constructors
    public Customer(String name, int accountNumber, double balance) {
        super(name);
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Customer(String name, int accountNumber, double balance, String bankName) {
        super(name);
        this.accountNumber = accountNumber;
        this.balance = balance;
        Customer.bankName = bankName;
    }

    // Getter and setter methods

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public static String getBankName() {
        return bankName;
    }

    public static void setBankName(String bankName) {
        Customer.bankName = bankName;
    }

    // Additional methods
    public void deposit(double amount) {
        balance += amount;
        System.out.println(amount + " deposited successfully. Current balance: " + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(amount + " withdrawn successfully. Current balance: " + balance);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void transfer(Customer recipient, double amount) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            System.out.println(amount + " transferred successfully to " + recipient.name);
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

    public void checkAccountDetails() {
        System.out.println("Account Details:");
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        System.out.println("Bank Name: " + bankName);
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

class Employee extends Human {
    private int pin;

    public Employee(String name, int pin) {
        super(name);
        this.pin = pin;
    }

    public boolean verifyPin(int inputPin) {
        return pin == inputPin;
    }

    public void closeAccount(Customer[] customers, String name, int accountNumber) {
        for (int i = 0; i < customers.length; i++) {
            if (customers[i] != null && customers[i].name.equals(name) && customers[i].getAccountNumber() == accountNumber) {
                customers[i] = null;
                System.out.println("Account closed successfully.");
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public void openAccount(Customer[] customers, String name, int accountNumber, double initialBalance) {
        for (int i = 0; i < customers.length; i++) {
            if (customers[i] == null) {
                customers[i] = new Customer(name, accountNumber, initialBalance);
                System.out.println("Account opened successfully.");
                return;
            }
        }
        System.out.println("All slots are full. Cannot open new account.");
    }

    public void modifyAccount(Customer[] customers, int accountNumber, String newName, int newAccountNumber, double newBalance) {
        for (Customer customer : customers) {
            if (customer != null && customer.getAccountNumber() == accountNumber) {
                customer.setName(newName);
                customer.setAccountNumber(newAccountNumber);
                customer.setBalance(newBalance);
                System.out.println("Account details modified successfully.");
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public void transfer(Customer[] customers, int senderAccountNumber, int recipientAccountNumber, double amount) {
        Customer sender = findCustomer(customers, senderAccountNumber);
        Customer recipient = findCustomer(customers, recipientAccountNumber);
        if (sender != null && recipient != null) {
            sender.withdraw(amount);
            recipient.deposit(amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    // Method to find customer by account number
    private Customer findCustomer(Customer[] customers, int accountNumber) {
        for (Customer customer : customers) {
            if (customer != null && customer.getAccountNumber() == accountNumber) {
                return customer;
            }
        }
        return null;
    }
}

public class BankingSystem {
    private static final int MAX_CUSTOMERS = 5; // Maximum number of customers
    private static final String DEFAULT_BANK_NAME = "VesBank"; // Default bank name

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Create an array to store multiple customers
        Customer[] customers = new Customer[MAX_CUSTOMERS];
        customers[0] = new Customer("Sahil", 1001, 10000, DEFAULT_BANK_NAME);
        customers[1] = new Customer("Sudarshan", 1002, 10000, DEFAULT_BANK_NAME);
        customers[2] = new Customer("Yash", 1003, 10000, DEFAULT_BANK_NAME);
        customers[3] = new Customer("Harshit", 1004, 10000, DEFAULT_BANK_NAME);
        customers[4] = new Customer("Rohit", 1005, 10000, DEFAULT_BANK_NAME);

        System.out.println("Welcome to the Banking Management System.");
        System.out.println("1. Customer");
        System.out.println("2. Employee");
        System.out.print("Choose an option: ");
        choice = scanner.nextInt();

        if (choice == 1) {
            System.out.print("Enter your account number: ");
            int accountNumber = scanner.nextInt();
            Customer customer = findCustomer(customers, accountNumber);
            if (customer != null) {
                System.out.println("1. Deposit Money");
                System.out.println("2. Withdraw Money");
                System.out.println("3. Transfer Money");
                System.out.println("4. Check Account Details");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        customer.deposit(depositAmount);
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        customer.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter recipient's account number: ");
                        int recipientAccountNumber = scanner.nextInt();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        Customer recipient = findCustomer(customers, recipientAccountNumber);
                        if (recipient != null) {
                            customer.transfer(recipient, transferAmount);
                        } else {
                            System.out.println("Recipient account not found.");
                        }
                        break;
                    case 4:
                        customer.checkAccountDetails();
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } else if (choice == 2) {
            Employee employee = new Employee("Jane Doe", 1234); // Sample employee data
            System.out.print("Enter Employee PIN: ");
            int pinAttempt = scanner.nextInt();
            if (employee.verifyPin(pinAttempt)) {
                System.out.println("Employee verified successfully.");
                System.out.println("1. Close Account");
                System.out.println("2. Open New Account");
                System.out.println("3. Modify Account");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        System.out.print("Enter customer's name: ");
                        String closeName = scanner.next();
                        System.out.print("Enter customer's account number: ");
                        int closeAccountNumber = scanner.nextInt();
                        employee.closeAccount(customers, closeName, closeAccountNumber);
                        break;
                    case 2:
                        System.out.print("Enter customer's name: ");
                        String openName = scanner.next();
                        System.out.print("Enter customer's account number: ");
                        int openAccountNumber = scanner.nextInt();
                        System.out.print("Enter initial balance: ");
                        double initialBalance = scanner.nextDouble();
                        employee.openAccount(customers, openName, openAccountNumber, initialBalance);
                        break;
                    case 3:
                        System.out.print("Enter account number to modify: ");
                        int modifyAccountNumber = scanner.nextInt();
                        System.out.print("Enter new name: ");
                        String newName = scanner.next();
                        System.out.print("Enter new account number: ");
                        int newAccountNumber = scanner.nextInt();
                        System.out.print("Enter new balance: ");
                        double newBalance = scanner.nextDouble();
                        employee.modifyAccount(customers, modifyAccountNumber, newName, newAccountNumber, newBalance);
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } else {
                System.out.println("Incorrect PIN. Access denied.");
            }
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }

    // Method to find customer by account number
    private static Customer findCustomer(Customer[] customers, int accountNumber) {
        for (Customer customer : customers) {
            if (customer != null && customer.getAccountNumber() == accountNumber) {
                return customer;
            }
        }
        return null;
    }
}
