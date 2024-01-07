package com.example.javafx_bank.incident;

import java.text.DecimalFormat;

/**
 * @author Yucong Liu, Phillip Huang
 */

public class CollegeChecking extends Checking {
    private Campus campus;

    /**
     * Constructor for the subclass CollegeChecking
     * @param holder
     * @param balance
     * @param campus
     */
    public CollegeChecking(Profile holder, double balance, Campus campus) {
        super(holder, balance);
        this.campus = campus;
    }

    /**
     * Monthly fee of 0 for college checking
     * @return 0
     */
    @Override
    public double monthlyFee() {
        return 0;
    }

    /**
     * Get the Profile of college checking account and format it into a String
     * @return String of Account's Profile
     */
    @Override
    public String toString() {
        return holder + "(CC)";
    }

    /**
     * Enumeration of different campuses
     */
    public enum Campus {
        NEW_BRUNSWICK, NEWARK, CAMDEN
    }

    /**
     * Compare current account with Object o
     * @param o
     * @return true if o is an instance of "CollegeChecking" and has the same holder as account, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass().getSimpleName().equals("CollegeChecking")){
            CollegeChecking checking = (CollegeChecking) o;
            return holder.equals(checking.holder);
        }
        return false;
    }

    /**
     * Getter for Campus
     * @return campus
     */
    public Campus getCampus() {
        return campus;
    }

    /**
     * Get the holder's first and last name, date of birth, balance, and campus name
     * @return formatted String of account holder's first and last name, date of birth, balance, and campus name
     */
    @Override
    public String getToSortString() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedBalance = decimalFormat.format(balance);
        return "College Checking::" + holder.getFname() + " " + holder.getLname() + " " +
                holder.getDob().toString() + "::Balance $" + formattedBalance + "::" +
                campus.name();
    }

    /**
     * Get the holder's name, date of birth, balance, campus name, fee, and monthly interest
     * @return formatted String of account holder's name, date of birth, balance, campus name, fee,
     *          and monthly interest
     */
    @Override
    public String detailedStatement() {
        double fee = 0;
        double monthlyInterest =monthlyInterest();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedBalance = decimalFormat.format(balance);
        String formattedFee = decimalFormat.format(fee);
        String formattedInterest = decimalFormat.format(monthlyInterest);
        return "College Checking::" + holder.toString() + "::Balance $" + formattedBalance +
                "::"+getCampus().name()+
                "::fee $" + formattedFee + "::monthly interest $" + formattedInterest;
    }
}
