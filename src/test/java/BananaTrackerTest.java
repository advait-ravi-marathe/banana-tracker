import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BananaTrackerTest {
    private BananaTracker tracker;

    @BeforeEach
    public void setUp() {
        tracker = new BananaTracker();
        // Since 'users' is static and might persist between tests, we need to be careful.
        // But for these basic tests it should be fine if we use unique emails.
    }

    @Test
    public void testSignUpSuccess() {
        boolean result = tracker.signUp("Jane", "Doe", "jane.doe@example.com", "pass", false);
        assertTrue(result);
    }

    @Test
    public void testSignUpDuplicateEmail() {
        tracker.signUp("User1", "Test", "duplicate@example.com", "pass", false);
        boolean result = tracker.signUp("User2", "Test", "duplicate@example.com", "pass", false);
        assertFalse(result);
    }

    @Test
    public void testSignInSuccess() {
        tracker.signUp("Login", "User", "login@example.com", "secret", false);
        boolean result = tracker.signIn("login@example.com", "secret");
        assertTrue(result);
        assertEquals("login@example.com", tracker.getAuthenticatedUser().getUserEmail());
    }

    @Test
    public void testSignInFailure() {
        tracker.signUp("Login", "User", "fail@example.com", "secret", false);
        boolean result = tracker.signIn("fail@example.com", "wrongpass");
        assertFalse(result);
    }

    @Test
    public void testSignOut() {
        tracker.signUp("Logout", "User", "logout@example.com", "pass", false);
        tracker.signIn("logout@example.com", "pass");
        assertNotNull(tracker.getAuthenticatedUser());
        
        tracker.signOut();
        assertNull(tracker.getAuthenticatedUser());
    }

    @Test
    public void testAddTransactionAuthenticated() throws Exception {
        tracker.signUp("Txn", "User", "txn@example.com", "pass", false);
        tracker.signIn("txn@example.com", "pass");
        
        tracker.addTransaction("Shop", 20.0, "2023-10-27 12:00:00");
        
        User authUser = tracker.getAuthenticatedUser();
        assertEquals(1, authUser.getTransactions().size());
        assertEquals("Shop", authUser.getTransactions().get(0).recipientName);
    }

    @Test
    public void testAddTransactionUnauthenticated() {
        tracker.signOut();
        assertThrows(Exception.class, () -> {
            tracker.addTransaction("Shop", 20.0, "2023-10-27 12:00:00");
        });
    }
}
