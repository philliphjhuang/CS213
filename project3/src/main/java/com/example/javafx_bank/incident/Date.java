package com.example.javafx_bank.incident;

/**
 * @author Yucong Liu, Phillip Huang
 */


public class Date implements Comparable<Date>{
    private int day;
    private int month;
    private int year;

    /**
     * Constructor for Date
     * @param day
     * @param month
     * @param year
     */
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Test if a date is valid
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        if (year < 1900 || month < 1 || month > 12 || day < 1) {
            return false;
        }
        int maxDay = 31;
        if (month == 2) {
            maxDay = isLeapYear() ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDay = 30;
        }
        if (day > maxDay) {
            return false;
        }
        if (day>31){
            return false;
        }
        return true;
    }

    /**
     * Check if a year is a leap year
     * @return true if year is divisible by 4, false otherwise
     */
    private boolean isLeapYear() {
        if (year % 400 == 0) return true;
        if (year % 100 == 0) return false;
        return year % 4 == 0;
    }

    /**
     * Format the date in month/day/year
     * @return String of month, day, and year
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
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
     * Compare two dates year, month, and day
     * @param other the Date to be compared.
     * @return 0 if they are the same, otherwise the difference of year, month, or day
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        }
        if (this.month != other.month) {
            return this.month - other.month;
        }
        return this.day - other.day;
    }

    /**
     * Compare Date with Obeject o
     * @param o
     * @return true if they are the same, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Date)) return false;
        Date date = (Date) o;
        return day == date.day && month == date.month && year == date.year;
    }
}
