import java.util.ArrayList;
import java.util.Scanner;

public class BananaTracker {
    private static final ArrayList<User> users = new ArrayList<>();
    private static User authenticatedUser = null;

    public boolean signUp(String firstName, String lastName, String email, String password, boolean isAdmin) {
        if (email == null || email.isEmpty()) return false;
        for (User user : users) {
            if (user.getUserEmail().equals(email)) return false;
        }
        User user = new User(firstName, lastName, email, password, isAdmin);
        users.add(user);
        return true;
    }

    public boolean signIn(String email, String password) {
        for (User user : users) {
            if (user.getUserEmail().equals(email) && user.getUserPassword().equals(password)) {
                authenticatedUser = user;
                return true;
            }
        }
        return false;
    }

    public void signOut() {
        authenticatedUser = null;
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }

    public static void signUpUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("**** SIGN UP ****");
        System.out.print("Enter First Name: ");
        String userFirstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String userLastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String userEmail = scanner.nextLine();
        System.out.print("Enter Password: ");
        String userPassword = scanner.nextLine();
        System.out.print("Is Admin (true/false): ");
        boolean isAdmin = false;
        if (scanner.hasNextBoolean()) {
            isAdmin = scanner.nextBoolean();
            scanner.nextLine(); // consume newline
        } else {
            String input = scanner.next();
            scanner.nextLine(); // consume newline
            System.out.println("Invalid input for isAdmin: " + input + ". Defaulting to false.");
        }

        BananaTracker repo = new BananaTracker();
        if (repo.signUp(userFirstName, userLastName, userEmail, userPassword, isAdmin)) {
            System.out.println("User signed up successfully!");
        } else {
            System.out.println("Sign up failed. Email might already exist.");
        }
    }

    public static void signInUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("**** SIGN IN ****");
        System.out.print("Enter Email: ");
        String userEmail = scanner.nextLine();
        System.out.print("Enter Password: ");
        String userPassword = scanner.nextLine();

        BananaTracker repo = new BananaTracker();
        if (repo.signIn(userEmail, userPassword)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    public static void signOutUser() {
        System.out.println("**** SIGN OUT ****");
        System.out.print("Are you sure you want to log out? ");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
            new BananaTracker().signOut();
            System.out.println("You have been signed out.");
        } else {
            System.out.println("Sign out canceled.");
        }
    }

    public static void showProfile() {
        System.out.println("**** PROFILE ****");
        if (authenticatedUser == null) {
            System.out.println("You are not logged in.");
            return;
        }
        System.out.println("User ID: " + authenticatedUser.getUserId());
        System.out.println("Email: " + authenticatedUser.getUserEmail());
        System.out.println("Name: " + authenticatedUser.getUserFullName());
    }

    public static void showUserTransactions() {
        System.out.println("**** TRANSACTIONS ****");
        if (authenticatedUser == null) {
            System.out.println("You are not logged in.");
            return;
        }
        authenticatedUser.displayTransactionsTable();
    }

    public void addTransaction(String recipientName, double amount, String transactionDate) throws Exception {
        if (authenticatedUser == null) {
            throw new Exception("Authentication required to add transactions.");
        }
        try {
            authenticatedUser.getTransactions().add(new Transaction(recipientName, amount, transactionDate));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public static void showMenu() {
        BananaTracker repo = new BananaTracker();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Banana Tracker v1.0 ---");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Show Profile");
            System.out.println("4. Show Transactions");
            System.out.println("5. Add Transaction");
            System.out.println("6. Show All Users (Admin Only)");
            System.out.println("7. Sign Out");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } else {
                String input = scanner.next();
                System.out.println("Invalid input: " + input + ". Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    signUpUser();
                    break;
                case 2:
                    signInUser();
                    break;
                case 3:
                    showProfile();
                    break;
                case 4:
                    showUserTransactions();
                    break;
                case 5:
                    addTransactionMenu(repo, scanner);
                    break;
                case 6:
                    showAllUsers();
                    break;
                case 7:
                    signOutUser();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void showAllUsers() {
        if (authenticatedUser == null || !authenticatedUser.isAdmin()) {
            System.out.println("Access denied. Admin privileges required.");
            return;
        }

        if (users == null || users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.printf("%-38s | %-15s | %-15s | %-25s | %-20s | %-10s | %-50s%n",
                "ID", "FIRST_NAME", "LAST_NAME", "EMAIL", "PASSWORD", "IS_ADMIN", "TRANSACTIONS (JSON)");
        System.out.println("-".repeat(180));

        for (User user : users) {
            user.displayUser();
        }
    }

    private static void addTransactionMenu(BananaTracker repo, Scanner scanner) {
        System.out.print("Enter Recipient Name: ");
        String recipient = scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = 0;
        if (scanner.hasNextDouble()) {
            amount = scanner.nextDouble();
            scanner.nextLine(); // consume newline
        } else {
            System.out.println("Invalid amount.");
            scanner.nextLine(); // consume newline
            return;
        }
        System.out.print("Enter Date (yyyy-MM-dd HH:mm:ss): ");
        String date = scanner.nextLine();

        try {
            repo.addTransaction(recipient, amount, date);
            System.out.println("Transaction added successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
