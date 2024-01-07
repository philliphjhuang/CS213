import java.text.DecimalFormat;

/**
 * @author Yucong Liu, Phillip Huang
 */


public class MoneyMarket extends Savings {
    private static final double INTEREST_RATE = 0.045;
    private static final double LOYAL_INTEREST_RATE = 0.0475;
    private static final double MIN_BALANCE_FOR_LOYAL = 2000;
    private int withdrawal;

    /**
     * Constructor for the subclass MoneyMarket. Initialize withdrawal amount to 0
     * @param holder
     * @param balance
     */
    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance, true);
        this.withdrawal = 0;
    }

    /**
     * Calculates and returns the monthly interest of the account.
     *     Rate depends on whether the account balance is greater or equal to MIN_BALANCE_FOR_LOYAL).
     *     If loyal, the LOYAL_INTEREST_RATE is used;
     * @return monthly interest for the Checking account
     */
    @Override
    public double monthlyInterest() {
        double rate = balance >= MIN_BALANCE_FOR_LOYAL ? LOYAL_INTEREST_RATE : INTEREST_RATE;
        return balance * rate / 12;
    }

    /**
     * Calculates and return the monthly fee
     * @return 10 if balance is greater and equal to 2000 and withdrawal greater than 3, 35 if it is just withdrawal
     *             is greater than 3, 25 if balance less than 2000 and withdrawal less than equal to 3.
     *             Otherwise 0 if balance is greater and equal to MIN_BALANCE_FOR_LOYAL, false otherwise
     */
    @Override
    public double monthlyFee() {
        if (balance>=2000&&withdrawal>3){
            return 10;
        }else if(withdrawal>3){
            return 35;
        }else  if(balance<2000&&withdrawal<=3){
            return 25;
        }
        return balance >= MIN_BALANCE_FOR_LOYAL ? 0 : 10;
    }

    /**
     * Withdraw amount from an account by decreasing the account's balance by amount
     * @param amount
     */
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            withdrawal++;
            if (balance < MIN_BALANCE_FOR_LOYAL) {
                isLoyal = false;
            }
        }
    }

    /**
     * Deposit amount from an account by increasing the account's balance by amount
     * @param amount
     */
    public void deposit(double amount) {
            balance += amount;
            if (balance >=MIN_BALANCE_FOR_LOYAL) {
                isLoyal = true;
            }
    }

    /**
     * Format MoneyMarket's Profile to a String
     * @return String of holder
     */
    @Override
    public String toString() {
        return holder + "(MM)";
    }

    /**
     * Compares a MoneyMarket account to Object o
     * @param o
     * @return true if date of birth of account is the same as o's, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o.getClass().getSimpleName().contains("MoneyMarket"))) {
            Account account = (Account) o;
            boolean equals = holder.equals(account.holder);
            if (equals){
                return holder.getDob().equals(account.getHolder().getDob());
            }
            return  false;
        }
        return false;
    }

    /**
     * Getter for withdrawal
     * @return withdrawal
     */
    public int getWithdrawal() {
        return withdrawal;
    }

    /**
     * Setter for withdrawal
     * @param withdrawal
     */
    public void setWithdrawal(int withdrawal) {
        this.withdrawal = withdrawal;
    }

    /**
     * Formats a String of MoneyMarket account's first and last name, date of birth, loyalty, and withdrawal
     * @return String
     */
    @Override
    public String getToSortString() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String balanceFormatted = decimalFormat.format(getBalance());
        String isLoyal=isLoyal()?"::is loyal":"";
        return "Money Market::Savings::" + holder.getFname() + " " + holder.getLname() + " " +
                holder.getDob().toString() + "::Balance $" + balanceFormatted +isLoyal+
                "::withdrawal: " + getWithdrawal();
    }

    /**
     * Formats a String of MoneyMarket account's profile, balance, loyalty, fee, and monthly interest
     * @return String
     */
    @Override
    public String detailedStatement() {
        double fee = monthlyFee();
        double monthlyInterest = monthlyInterest();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedBalance = decimalFormat.format(balance);
        String formattedFee = decimalFormat.format(fee);
        String formattedInterest = decimalFormat.format(monthlyInterest);
        String isLoyal = isLoyal() ? "::is loyal" : "";
        String withdrawal = "withdrawal: " + getWithdrawal();
        return "Money Market::Savings::" + holder.toString() + "::Balance $" + formattedBalance +
                isLoyal + "::" + withdrawal +
                "::fee $" + formattedFee + "::monthly interest $" + formattedInterest;
    }
}
