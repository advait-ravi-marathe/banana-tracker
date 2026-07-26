import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testTransactionCreationSuccess() {
        String recipient = "Alice";
        double amount = 50.5;
        String dateStr = "2023-10-27 10:00:00";
        
        Transaction transaction = new Transaction(recipient, amount, dateStr);
        
        assertEquals(recipient, transaction.recipientName);
        assertEquals(amount, transaction.amount);
        assertNotNull(transaction.transactionDate);
        assertNotNull(transaction.transactionId);
    }

    @Test
    public void testTransactionCreationInvalidDate() {
        Transaction transaction = new Transaction("Bob", 10.0, "invalid-date");
        assertNull(transaction.transactionDate);
    }
}
