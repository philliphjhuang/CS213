package project1;

/**
 * @author Yucong Liu, Phillip Huang
 * @create 2023/9/19 7:28
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    public static final int[] BIG = {1, 3, 5, 7, 8, 10, 12};
    public static final int[] SMALL = {4, 6, 9, 11};

    /**
     * Constructor for Date
     * @param year as int
     * @param month as int
     * @param day as int
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Constructor for Date
     * @param date as String
     */
    public Date(String date) {
        String[] splitDate = date.split("/",3);
        this.year = Integer.valueOf(splitDate[2]);
        this.month = Integer.valueOf(splitDate[0]);
        this.day = Integer.valueOf(splitDate[1]);
    }

    /**
     * Compare two events' year, month, and day
     * @param o the Date to be compared.
     * @return 0 if they are the same, the difference between year,
     * month, or day if not 0
     */
    @Override
    public int compareTo(Date o) {
        if (this.year != o.year) {
            return this.year - o.year;
        } else if (this.month != o.month) {
            return this.month - o.month;
        } else if (this.day != o.day) {
            return this.day - o.day;
        } else {
            return 0;
        }
    }

    /**
     * Check if a month exists in BIG or SMALL array
     * @param arr  to be checked if month exists in there
     * @param month
     * @return true if month is in arr, false otherwise
     */
    public boolean isExist(int[] arr, int month) {
        for (int i = 0; i < arr.length; i++) {
            if (month == arr[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the year of a Date is after 1900, then check if it is leap year
     * @return false if not isAfter1900 or isLeapYear, true otherwise
     */
    public boolean isValid() {
        // Check it is after 1900
        if(!isAfter1900()) {
            return false;
        }
        //Check if it is leap year
        if(!isLeapYear()) {
            return false;
        }
        return true;
    }

    /**
     * Check if year is greater or equal to 1900
     * @return true if year greater or equal to 1900, false otherwise
     */
    public boolean isAfter1900() {
        return year>=1900;
    }

    /**
     * Check if event date is leap year
     * @return true for either day of 29 or 28
     */
    public boolean isLeapYear() {
        if (year % QUATERCENTENNIAL == 0 || year % QUADRENNIAL == 0 && year % CENTENNIAL != 0) {
            //Is leap year
            return isValidDate(29);
        } else {
            //Is not leap year
            return isValidDate(28);
        }
    }

    /**
     * Check if February, SMALL, and BIG months have their correct # of days
     * @param leap
     * @return For month value of 2: true if day is smaller or equal to leap and greater than 0, false otherwise.
     * For BIG months, true if day greater than 0 and smaller or equal to 31, false otherwise
     * For SMALL months, true if day greater than 0 and smaller or equal to 30, false otherwise
     */
    // Check if February, small, and big months have their correct # of days
    public boolean isValidDate(int leap) {
        if (month == 2) { //Check if the date is February
            if (day <= leap && day > 0) {
                return true;
            } else {
                return false;
            }
        } else if (isExist(BIG, month)) { //BIG months
            if (day <= 31 && day > 0) {
                return true;
            } else {
                return false;
            }
        } else if (isExist(SMALL, month)) { //SMALL months
            if (day <= 30 && day > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Getter for year
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Setter for year
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Getter for month
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Setter for month
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Getter for day
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * Setter for day
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Check if expectedOutput and actualOutput for a date is the same
     * @param date
     * @param expectedOutput
     * @param actualOutput
     * @return true if expectedOutput is the same as actualOutput, false otherwise
     */
    public static boolean testResult(Date date, boolean expectedOutput, boolean actualOutput) {
        System.out.println(expectedOutput == actualOutput);
        return expectedOutput == actualOutput;
    }

    /**
     * Change the format of Date into string
     * @return String of month, day, and year in format of month/day/year
     */
    @Override
    public String toString() {
        return month + "/" + day+ "/" + year;
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        testDaysInFeb_Nonleap();
        testDaysInFeb_Leap();
        testMonth_OutOfRange();
        testYearBefore1900();

        testDaysInFeb_Nonleap_2();
        testDaysInFeb_Leap_2();
        testMonth_OutOfRange_2();
        testYearBefore1900_2();
    }

    /**
     * Test case #1
     */
    public static void testDaysInFeb_Nonleap() {
        Date date = new Date("2/29/2011");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #1: # of days in Feb. in a non-leap year is 28");
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #2
     */
    public static void testDaysInFeb_Leap() {
        Date date = new Date("2/29/2020");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #2: # of days in Feb. in a leap year is 29");
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #3
     */
    public static void testMonth_OutOfRange() {
        Date date = new Date("13/28/2018");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #3: month value should be from 1 to 12");
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #4
     */
    public static void testYearBefore1900() {
        Date date = new Date("2/28/1899");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #4: year value has to be 1900 or higher");
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #5
     */
    public static void testDaysInFeb_Nonleap_2() {
        Date date = new Date("2/28/2011");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #5: # of days in Feb. in a non-leap year is 28");
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #6
     */
    public static void testDaysInFeb_Leap_2() {
        Date date = new Date("2/30/2020");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #6: # of days in Feb. in a leap year is 29");
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #7
     */
    public static void testMonth_OutOfRange_2() {
        Date date = new Date("12/28/2018");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #7: month value should be from 1 to 12");
        testResult(date, expectedOutput, actualOutput);
    }

    /**
     * Test case #8
     */
    public static void testYearBefore1900_2() {
        Date date = new Date("2/28/1900");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #8: year value has to be 1900 or higher");
        testResult(date, expectedOutput, actualOutput);
    }
}
