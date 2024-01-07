/**
 * @author Yucong Liu, Phillip Huang
 */
import static org.junit.Assert.*;

public class AccountDatabaseTest{
    private AccountDatabase accountDatabse = new AccountDatabase();

    /**
     * Test a true case for close()
     */
    @org.junit.Test
    public void testCloseTrue() {
        Profile profile = new Profile("John", "Doe", new Date(1,1,2000));
        Account account = new Checking(profile, 2000);
        accountDatabse.open(account);
        assertTrue(accountDatabse.close(account));
    }

    /**
     * Test a false case for close()
     */
    @org.junit.Test
    public void testCloseFalse() {
        Profile profile1 = new Profile("John", "Doe", new Date(1,1,2000));
        Profile profile2 = new Profile("Phillip", "Huang", new Date(20,5,2001));
        Account account1 = new Checking(profile1, 1000);
        Account account2 = new Checking(profile2, 2000);
        accountDatabse.open(account1);
        assertFalse(accountDatabse.close(account2));
    }
}