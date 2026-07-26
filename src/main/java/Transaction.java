import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;

public class Transaction {
    UUID transactionId;
    String recipientName;
    double amount;
    Date transactionDate;

    private UUID createTransactionId() {
        return UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId + "| Recipient: " + recipientName + "| Amount: ₹" + amount + ", Date: " + transactionDate;
    }

    private Date createTransactionDate(String transactionDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(transactionDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public Transaction(String recipientName, double amount, String transactionDate) {
        this.transactionId = createTransactionId();
        this.recipientName = recipientName;
        this.amount = amount;
        try{
            this.transactionDate = createTransactionDate(transactionDate);
        } catch (ParseException e) {
            System.out.printf("Unable to parse transaction date: %s", e.getMessage());
            this.transactionDate = null;
        }
    }
}
