import java.text.DecimalFormat;

/**
 * @author Yucong Liu, Phillip Huang
 */


public class Savings extends Account {
    private static final double INTEREST_RATE = 0.04;
    private static final double LOYAL_INTEREST_RATE = 0.0425;
    private static final double FEE = 25;
    private static final double MIN_BALANCE_FOR_NO_FEE = 500;
    protected boolean isLoyal;

    /**
     * Constructor for subclass Savings
     * @param holder
     * @param balance
     * @param isLoyal
     */
    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     * Getter for isLoyal
     * @return isLoyal
     */
    public boolean isLoyal() {
        return isLoyal;
    }

    /**
     * Setter for isLoyal
     * @param loyal
     */
    public void setLoyal(boolean loyal) {
        isLoyal = loyal;
    }

    /**
     * Calculates the monthly interest based on account's balance and loyal
     * @return the calculated monthly interest
     */
    @Override
    public double monthlyInterest() {
        double rate = isLoyal ? LOYAL_INTEREST_RATE : INTEREST_RATE;
        return balance * rate / 12;
    }

    /**
     * Calculate monthly fee based on balance, MIN_BALANCE_FOR_NO_FEE, and FEE
     * @return the calculated monthly fee
     */
    @Override
    public double monthlyFee() {
        return balance >= MIN_BALANCE_FOR_NO_FEE ? 0 : FEE;
    }

    /**
     * Compares Savings with Object o
     * @param o
     * @return true if Savings and o are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o.getClass().getSimpleName().contains("Savings"))) {
            Account account = (Account) o;
            return  holder.equals(account.holder);
        }
        return false;
    }

    /**
     * Get Savings account's Profile as a String
     * @return holder of Savings
     */
    @Override
    public String toString() {
        return  holder + "(S)";
    }

    /**
     * Formats a String of Savings account's holder's name, date of birth, and balance
     * @return String of Savings account's holder's name, date of birth, and balance
     */
    @Override
    public String getToSortString() {
        String loyalty = isLoyal() ? "::is loyal" : "";
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String balanceFormatted = decimalFormat.format(getBalance());
        return "Savings::" + holder.getFname() + " " + holder.getLname() + " " +
                holder.getDob().toString() + "::Balance $" + balanceFormatted + loyalty;
    }

    /**
     * Formats a String of Savings account's holder's savings, balance, loyal, fee, and monthly interest
     * @return String of Savings account's holder's savings, balance, loyal, fee, and monthly interest
     */
    @Override
    public String detailedStatement() {
        double fee = balance < 500 ? 25 : 0;
        double monthlyInterest = monthlyInterest();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String balanceFormatted = decimalFormat.format(getBalance());
        String formattedFee = String.format("%.2f", fee);
        String formattedInterest = String.format("%.2f", monthlyInterest);
        String isLoyal=isLoyal()?"::is loyal":"";
        return "Savings::" + holder.toString() + "::Balance $" + balanceFormatted +
                isLoyal+
                "::fee $" + formattedFee + "::monthly interest $" + formattedInterest;
    }
}
