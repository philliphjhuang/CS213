/**
 * @author Yucong Liu, Phillip Huang
 */
import static org.junit.Assert.*;

public class DateTest {
    private Date date1 = new Date(29, 2, 2011);

    /**
     * Test an invalid day in a non-leap year
     */
    @org.junit.Test
    public void testIsValidFalse1() {
        assertFalse(date1.isValid());
    }

    /**
     * Test a valid day in a leap year
     */
    private Date date2 = new Date(29, 2, 2012);
    @org.junit.Test
    public void testIsValidTrue1() {
        assertTrue(date2.isValid());
    }

    /**
     * Test an invalid month
     */
    private Date date3 = new Date(5, 0, 2012);
    @org.junit.Test
    public void testIsValidFalse2() {
        assertFalse(date3.isValid());
    }

    /**
     * Test an invalid year
     */
    private Date date4 = new Date(20, 5, 1899);
    @org.junit.Test
    public void testIsValidFalse3() {
        assertFalse(date4.isValid());
    }

    /**
     * Test an invalid day in a leap year
     */
    private Date date5 = new Date(30, 2, 2012);
    @org.junit.Test
    public void testIsValidFalse4() {
        assertFalse(date5.isValid());
    }

    /**
     * Test a valid year
     */
    private Date date6 = new Date(5, 2, 20000);
    @org.junit.Test
    public void testIsValidTrue2() {
        assertTrue(date6.isValid());
    }

    /**
     * Test an invalid month
     */
    private Date date7 = new Date(5, 13, 2000);
    @org.junit.Test
    public void testIsValidFalse5() {
        assertFalse(date7.isValid());
    }
}
