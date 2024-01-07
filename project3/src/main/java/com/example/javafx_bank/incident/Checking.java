package com.example.javafx_bank.incident;

import java.text.DecimalFormat;

/**
 * @author Yucong Liu
 */


public class Checking extends Account {
    private static final double INTEREST_RATE = 0.01;
    private static final double FEE = 12;
    private static final double MIN_BALANCE_FOR_NO_FEE = 1000;

    /**
     * Constructor for the subclass Checking
     * @param holder
     * @param balance
     */
    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Compare current account with Object o
     * @param o
     * @return true if o is an instance of "Checking" and has the same holder as account, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass().getSimpleName().equals("Checking")) {
            Checking checking = (Checking) o;
            return holder.equals(checking.holder);
        }
        return false;
    }

    /**
     * Gets the account holder and balance from the superclass Account and turns them into a String.
     * @return String of Checking, including holder and balance
     */
    @Override
    public String toString() {
        return super.toString() + "(C)";
    }

    /**
     * Calculate and return the monthly interest based on the balance of the account and interest rate
     * @return double of the monthly interest
     */
    @Override
    public double monthlyInterest() {
        return balance * INTEREST_RATE / 12;
    }

    /**
     * Calculates and returns the monthly fee for the Checking account. Fee is 0 if the balance is greater than or
     *      equal to minimum balance.
     * @return double of monthly fee for the Checking account
     */
    @Override
    public double monthlyFee() {
        return balance >= MIN_BALANCE_FOR_NO_FEE ? 0 : FEE;
    }

    /**
     * Get the holder's first and last name, date of birth, and balance
     * @return formatted String of Checking account's person name, date, and balance
     */
    @Override
    public String getToSortString() {
        double balance = getBalance();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedBalance = decimalFormat.format(balance);
        return "Checking::" + holder.getFname() + " " + holder.getLname() + " " +
                holder.getDob().toString() + "::Balance $" + formattedBalance;
    }

    /**
     * Get the holder's full name and date, balance, fee, and monthly interest
     * @return formatted String of Checking account's person name and date, balance, fee, and monthly interest
     */
    @Override
    public String detailedStatement() {
        double fee = balance < 1000 ? 12 : 0;
        double monthlyInterest = monthlyInterest();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedBalance = decimalFormat.format(balance);
        String formattedFee = decimalFormat.format(fee);
        String formattedInterest = decimalFormat.format(monthlyInterest);
        return "Checking::" + holder.toString() + "::Balance $" + formattedBalance +
                "::fee $" + formattedFee + "::monthly interest $" + formattedInterest;
    }
}
