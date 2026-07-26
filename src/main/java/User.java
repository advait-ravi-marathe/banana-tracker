import java.util.ArrayList;
import java.util.UUID;

public class User {
    UUID userId;
    String userFirstName;
    String userLastName;
    String userEmail;
    String userPassword;
    ArrayList<Transaction> transactions;
    boolean isAdmin;

    private UUID generateUserId() {
        return UUID.randomUUID();
    }

    private String encryptData(String data) {
        return java.util.Base64.getEncoder().encodeToString(data.getBytes());
    }

    private String decryptData(String data) {
        return new String(java.util.Base64.getDecoder().decode(data));
    }

    public User(String userFirstName, String userLastName, String userEmail, String userPassword, boolean isAdmin) {
        this.userId = generateUserId();
        this.userFirstName = encryptData(userFirstName);
        this.userLastName = encryptData(userLastName);
        this.userEmail = encryptData(userEmail);
        this.userPassword = encryptData(userPassword);
        this.transactions = new ArrayList<>();
        this.isAdmin = isAdmin;
    }

    public String getUserFirstName() {
        return decryptData(userFirstName);
    }

    public String getUserLastName() {
        return decryptData(userLastName);
    }

    public String getUserFullName() {
        return getUserFirstName() + " " + getUserLastName();
    }

    public String getUserEmail() {
        return decryptData(userEmail);
    }

    public String getUserPassword() {
        return decryptData(userPassword);
    }

    public UUID getUserId() {
        return userId;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void displayTransactionsTable() {
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.printf("%-38s | %-20s | %-20s%n", "ID", "RECIPIENT_NAME", "TXN_DATE");
        System.out.println("----------------------------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            System.out.printf("%-38s | %-20s | %-20s%n",
                    transaction.transactionId,
                    transaction.recipientName,
                    transaction.transactionDate != null ? transaction.transactionDate.toString() : "N/A");
        }
    }

    public void displayUser() {
        String txnsJson = formatTransactionsAsJson(this.transactions);
        System.out.printf("%-38s | %-15s | %-15s | %-25s | %-20s | %-10s | %-50s%n",
                this.userId,
                this.userFirstName,
                this.userLastName,
                this.userEmail,
                this.userPassword,
                this.isAdmin,
                txnsJson);
    }

    private String formatTransactionsAsJson(ArrayList<Transaction> transactions) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"txns\": [");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            sb.append("{");
            sb.append("\"id\":\"").append(t.transactionId).append("\", ");
            sb.append("\"recipient\":\"").append(t.recipientName).append("\", ");
            sb.append("\"amount\":").append(t.amount).append(", ");
            sb.append("\"date\":\"").append(t.transactionDate).append("\"");
            sb.append("}");
            if (i < transactions.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }


}
