package com.example.javafx_bank.incident;

/**
 * @author Yucong Liu, Phillip Huang
 */

public class AccountDatabase {
    private Account[] accounts;
    private int numAcct;

    /**
     * Sets up the initial state of an AccountDatabase object with an empty array of Account objects and a numAcct of 0.
     */
    public AccountDatabase() {
        accounts = new Account[4];
        numAcct = 0;
    }

    /**
     * Search for a specific Account object in the accounts array
     * @param account
     * @return index i of the Account object if found in the accounts array, -1 otherwise
     */
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Increase the size of the accounts array by 4
     */
    private void grow() {
        Account[] newAccounts = new Account[accounts.length + 4];
        for (int i = 0; i < accounts.length; i++) {
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }

    /**
     * Check if an Account object is found in the accounts array
     * @param account
     * @return true if found, false otherwise
     */
    public boolean contains(Account account) {
        return find(account) != -1;
    }

    /**
     * Attempts to open a new account and add it to accounts array.
     * @param account
     * @return true if the account was successfully added, false if it already exists
     *            or if the database is at its capacity and couldn't grow
     */
    public boolean open(Account account) {
        if (contains(account)) {
            return false;
        }
        if(numAcct == accounts.length) {
            grow();
        }
        accounts[numAcct] = account;
        numAcct++;
        return true;
    }

    /**
     * Attempts to close an existing account and remove it from the accounts array.
     * @param account
     * @return true if the account was successfully closed and removed, false if the account
     *            doesn't exist in the database
     */
    public boolean close(Account account) {
        int index = find(account);
        if (index == -1) {
            return false;
        }
        for (int i = index; i < numAcct - 1; i++) {
            accounts[i] = accounts[i + 1];
        }
        accounts[numAcct - 1] = null;
        numAcct--;
        return true;
    }

    /**
     * Withdraw a specified amount from a specific account, if the account exists
     *   and has sufficient funds.
     * @param account
     * @param amount
     * @return 3 if the withdrawal was successful, 2 if the account does not have enough balance for the withdrawal
     *              amount, 1 if the account does not exist
     */
    public int withdraw(Account account, double amount) {
        int index = find(account);
        if (index == -1) {
            return 1;
        }
        if (accounts[index].balance < amount) {
            return 2;
        }
        if (accounts[index] instanceof MoneyMarket) {
            MoneyMarket mmAccount = (MoneyMarket) accounts[index];
            mmAccount.withdraw(amount);
        } else{
            accounts[index].balance -= amount;
        }
        return 3;
    }

    /**
     * Deposit a specified amount into a specific account if the account exists
     *    and the amount is non-negative. If the account is a MoneyMarket account, it
     *    deposits the amount into the MoneyMarket account, otherwise, it adds the amount
     *    to the account's balance.
     * @param account
     * @param amount
     * @return true if the deposit was successful, false if the account doesn't exist,
     *            the amount is negative, or if the account is not a MoneyMarket account
     */
    public boolean deposit(Account account, double amount) {
        if (account==null)return false;
        int index = find(account);
        if (index == -1) {
            return false;
        }
        if (account instanceof MoneyMarket){
            MoneyMarket moneyMarket =(MoneyMarket) accounts[index];
            moneyMarket.deposit(amount);
        }else{
            accounts[index].balance += amount;
        }
        return true;
    }

    /**
     * Sort the accounts array by account type and profile, then prints the sorted array
     *    If the database is empty, it displays a message indicating that.
     */
    public String printSorted() {
        StringBuilder sb = new StringBuilder();

        if (numAcct == 0) {
            return "Account Database is empty!";
        }

        sb.append("\n*Accounts sorted by account type and profile.\n");
        Account[] sortedAccounts = copyAccounts();
        bubbleSort(sortedAccounts);

        for (Account account : sortedAccounts) {
            if (account != null) {
                sb.append(account.getToSortString()).append("\n");
            }
        }

        sb.append("*end of list.\n");


        return sb.toString();
    }


    /**
     * Creates a copy of the all Account stored in the accounts array and returns it as a new array.
     * @return accounts array as new a array
     */
    private Account[] copyAccounts() {
        Account[] copiedAccounts = new Account[numAcct];
        for (int i = 0; i < numAcct; i++) {
            copiedAccounts[i] = accounts[i];
        }
        accounts=copiedAccounts;
        return copiedAccounts;
    }

    /**
     * Use bubble sort to sort the accounts array by Account and Profile names
     * @param accounts that is sorted
     */
    private void bubbleSort(Account[] accounts) {
        int n = accounts.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (accounts[j].compareTo(accounts[j + 1]) > 0) {
                    // swap temp and accounts[i]
                    Account temp = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Print that accounts array is empty if numAcct is 0, otherwise print the detailed statement of the sorted
     *      accounts array
     */
    public String printFeesAndInterests() {
        StringBuilder result = new StringBuilder();

        if (numAcct == 0) {
            result.append("Account Database is empty!\n");
            return result.toString();
        }

        result.append("\n*list of accounts with fee and monthly interest\n");
        Account[] sortedAccounts = copyAccounts();
        bubbleSort(sortedAccounts);

        for (Account account : sortedAccounts) {
            if (account != null) {
                result.append(account.detailedStatement()).append("\n");
            }
        }
        result.append("*end of list.\n");

        return result.toString();
    }


    /**
     * First check if accounts array is empty. If not, bubble sort the accounts array and print out each account's
     *      new balance after applying interests and fees
     */
    public String printUpdatedBalances() {
        StringBuilder result = new StringBuilder();

        if (numAcct == 0) {
            result.append("Account Database is empty!");
            return result.toString();
        }
        result.append("\n*list of accounts with fees and interests applied.\n");
        bubbleSort(accounts);
        for (int i = 0; i < numAcct; i++) {
            Account account = accounts[i];
            double interest = account.monthlyInterest();
            double fee = account.monthlyFee();
            account.balance += interest - fee;

            if (account instanceof MoneyMarket) {
                MoneyMarket moneyMarket = (MoneyMarket) account;
                moneyMarket.setWithdrawal(0);

                if (account.balance >= 2000) {
                    moneyMarket.setLoyal(true);
                }
            }
            result.append(account.getToSortString()).append("\n");
        }
        result.append("*end of list.\n");
        return result.toString();
    }
}
