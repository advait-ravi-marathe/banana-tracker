import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreationAndDecryption() {
        User user = new User("John", "Doe", "john.doe@example.com", "password123", false);
        
        assertEquals("John", user.getUserFirstName());
        assertEquals("Doe", user.getUserLastName());
        assertEquals("john.doe@example.com", user.getUserEmail());
        assertEquals("password123", user.getUserPassword());
        assertEquals("John Doe", user.getUserFullName());
        assertFalse(user.isAdmin());
        assertNotNull(user.getUserId());
        assertTrue(user.getTransactions().isEmpty());
    }

    @Test
    public void testSetAdmin() {
        User user = new User("Admin", "User", "admin@example.com", "admin", false);
        assertFalse(user.isAdmin());
        user.setAdmin(true);
        assertTrue(user.isAdmin());
    }
}
