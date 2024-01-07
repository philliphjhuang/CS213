/**
 * @author Yucong Liu, Phillip Huang
 */

public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    /**
     * Constructor for Account
     * @param holder as Profile
     * @param balance as double
     */
    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * Abstract method for calculating and returning the monthly interest earned on the account.
     * @return the monthly interest earned on the account
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method for calculating and returning the monthly fee associated with the account.
     * @return the monthly fee of the account
     */
    public abstract double monthlyFee();

    /**
     * Compares two accounts' class names and holder names
     * @param other the object to be compared.
     * @return 0 if they have the same name, difference of their name otherwise
     */
    @Override
    public int compareTo(Account other) {
        String thisClassName = this.getClass().getSimpleName();
        String otherClassName = other.getClass().getSimpleName();
        int typeComparison = thisClassName.compareTo(otherClassName);
        if (typeComparison != 0) {
            return typeComparison;
        }
        return this.holder.compareTo(other.holder);
    }

    /**
     * Abstract method that gets a String of the account for sorting.
     * @return returns a String of the account for sorting.
     */
    public abstract String getToSortString() ;

    /**
     * Abstract method that generates a detailed statement for account.
     * @return String of detailed statement of account with holder and balance
     */
    public abstract String detailedStatement();

    /**
     * Check if two accounts' holder are equal
     * @param o
     * @return true if the holders are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return  holder.equals(account.holder);
    }

    /**
     * Change holder from a Profile object to a String
     * @return holder as a String
     */
    @Override
    public String toString() {
        return holder+"";
    }

    /**
     * Getter for holder
     * @return holder as a Profile object
     */
    public Profile getHolder() {
        return holder;
    }

    /**
     * Getter for balance
     * @return balance as a double
     */
    public double getBalance() {
        return balance;
    }
}
