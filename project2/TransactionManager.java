import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Yucong Liu, Phillip Huang
 */

public class TransactionManager {
    private AccountDatabase accountDatabase;

    /**
     * Initialize accountDataBase
     */
    public TransactionManager() {
        accountDatabase = new AccountDatabase();
    }

    /**
     * Handles a closing account based on the information in the tokenizer.
     * @param tokenizer a StringTokenizer with account closure data
     */
    private void handleCloseAccount(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < 4) {
            System.out.println("Missing data for closing an account.");
            return;
        }
        String type = tokenizer.nextToken();
        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        String dob = tokenizer.nextToken();
        Date dateOfBirth= getDate(dob);
        if (dateOfBirth==null)return;
        Profile holder = new Profile(firstName, lastName, dateOfBirth);
        Account account = getTotalAccount(type, holder);
        if (account == null) return;
        if (accountDatabase.close(account)) {
            System.out.println(account.toString()+" has been closed.");
        } else {
            System.out.println(account.toString()+" is not in the database.");
        }
    }

    /**
     * Handles an opening account based on the information in the tokenizer.
     * @param tokenizer a StringTokenizer with account opening data
     */
    private void handleOpenAccount(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < 5) {
            System.out.println("Missing data for opening an account.");
            return;
        }
        String accountType = tokenizer.nextToken();
        String firstName = tokenizer.nextToken(), lastName = tokenizer.nextToken(), dob = tokenizer.nextToken();
        Double initialDeposit = getAmount(tokenizer,"Initial deposit");
        if (initialDeposit==null)return;
        Date dateOfBirth = getDate(dob);
        if (dateOfBirth == null) return;
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dateOfBirth.getYear();
        if (dateOfBirth.getMonth() > (today.get(Calendar.MONTH) + 1) || (dateOfBirth.getMonth() ==
                (today.get(Calendar.MONTH) + 1) && dateOfBirth.getDay() > today.get(Calendar.DAY_OF_MONTH))){
            age--;
        }
        if (age < 16) {
            System.out.println("DOB invalid: "+dateOfBirth+" under 16.");
            return;
        } else if (accountType.equals("CC") && age >= 24) {
            System.out.println("DOB invalid: "+dateOfBirth+" over 24.");
            return;
        }
        Profile holder = new Profile(firstName, lastName, dateOfBirth);
        Account account = getAccount(tokenizer, accountType, initialDeposit, holder), account2 = null;;
        if (account == null) return;
        if ("C".equals(accountType)){
            account2 = new CollegeChecking(account.getHolder(), 0, null);
        } else if ("CC".equals(accountType)){
            account2 = new Checking(account.getHolder(), 0);
        }
        if (account2 == null || (!accountDatabase.contains(account2) && !accountDatabase.contains(account))) {
            if (accountDatabase.open(account)) {
                System.out.println(account + " opened.");
            } else {
                System.out.println(account + " is already in the database.");
            }
        } else {
            System.out.println(account + " is already in the database.");
        }
    }

    /**
     * Handles the deposit process of an account.
     * @param tokenizer
     */
    private void handleDeposit(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < 5) {
            System.out.println("Invalid data for deposit");
            return;
        }
        String accountType = tokenizer.nextToken();
        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        String dob = tokenizer.nextToken();
        Double amount = getAmount(tokenizer,"Deposit - amount");
        if (amount==null) return;
        Date dateOfBirth = getDate(dob);
        if (dateOfBirth == null) return;
        Profile holder = new Profile(firstName, lastName, dateOfBirth);
        Account account = getTotalAccount(accountType, holder);
        if (account == null) return;
        if (accountDatabase.deposit(account, amount)) {
            System.out.println(account.toString()+" Deposit - balance updated.");
        } else {
            System.out.println(account.toString()+" is not in the database.");
        }
    }

    /**
     * Handles the withdraw process of an account.
     * @param tokenizer
     */
    private void handleWithdraw(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < 5) {
            System.out.println("Invalid data for withdrawal");
            return;
        }
        String accountType = tokenizer.nextToken();
        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        String dob = tokenizer.nextToken();
        Double amount = getAmount(tokenizer,"Withdraw - amount");
        if (amount == null) return;
        Date  dateOfBirth = getDate(dob);
        if (dateOfBirth==null)return;
        Profile holder = new Profile(firstName, lastName, dateOfBirth);
        Account account = getTotalAccount(accountType, holder);
        if (account == null) return;
        if (accountDatabase.withdraw(account, amount)) {
            System.out.println(account+" Withdraw - balance updated.");
        }
    }

    /**
     * Read from the input using Scanner
     */
    public void run() {
        System.out.println("Transaction Manager is running.");
        Scanner scanner= new Scanner(System.in);
        //Scanner scanner = new Scanner(System.in);
        while (true) {
            String commandLine = scanner.nextLine().trim();
            if (commandLine.isEmpty()) {
                continue;
            }
            StringTokenizer tokenizer = new StringTokenizer(commandLine);
            String command = tokenizer.nextToken();
            if (command(scanner, tokenizer, command)) return;
        }
    }

    /**
     * Method to track which command does what
     * @param scanner
     * @param tokenizer
     * @param command
     * @return true if the transaction manager gets Q command, false otherwise
     */
    private boolean command(Scanner scanner, StringTokenizer tokenizer, String command) {
        switch (command) {
            case "O":
                handleOpenAccount(tokenizer);
                break;
            case "C":
                handleCloseAccount(tokenizer);
                break;
            case "D":
                handleDeposit(tokenizer);
                break;
            case "W":
                handleWithdraw(tokenizer);
                break;
            case "P":
                accountDatabase.printSorted();
                break;
            case "PI":
                accountDatabase.printFeesAndInterests();
                break;
            case "UB":
                accountDatabase.printUpdatedBalances();
                break;
            case "Q":
                System.out.println("Transaction Manager is terminated.");
                scanner.close();
                return true;
            default:
                System.out.println("Invalid command!");
                break;
        }
        return false;
    }

    /**
     * Creates and returns an instance of the account (Checking, CollegeChecking, Savings, or Money Market)
     *    based on tokenizer, account type, initial deposit, and account holder profile.
     * @param tokenizer
     * @param accountType
     * @param initialDeposit
     * @param holder
     * @return instance of the specified account type, or null if the creation fails
     */
    private Account getAccount(StringTokenizer tokenizer, String accountType, Double initialDeposit, Profile holder) {
        Account account;
        switch (accountType) {
            case "C":
                account = new Checking(holder, initialDeposit);
                break;
            case "CC":
                CollegeChecking.Campus campus = getAccountHelperCC(tokenizer);
                if (campus==null) return null;
                account = new CollegeChecking(holder, initialDeposit, campus);
                break;
            case "S":
                if (tokenizer.countTokens() < 1) {
                    System.out.println("Missing loyalty status for savings account");
                    return null;
                }
                int isLoyal;
                try {
                    isLoyal = Integer.parseInt(tokenizer.nextToken());
                    if (isLoyal < 0 || isLoyal > 1) {
                        System.out.println("Invalid loyalty status");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid loyalty status format");
                    return null;
                }
                account = new Savings(holder, initialDeposit, isLoyal == 1);
                break;
            case "MM":
                if (initialDeposit < 2000) {
                    System.out.println("Minimum of $2000 to open a Money Market account.");
                    return null;
                }
                account = new MoneyMarket(holder, initialDeposit);
                break;
            default:
                System.out.println("Invalid account type");
                return null;
        }
        return account;
    }

    /**
     * Helper for getAccount method's CC command
     * @param tokenizer
     * @return campus
     */
    private CollegeChecking.Campus getAccountHelperCC(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < 1) {
            System.out.println("Missing campus code for college checking account.");
            return null;
        }
        int campusCode;
        try {
            campusCode = Integer.parseInt(tokenizer.nextToken());
            if (campusCode < 0 || campusCode > 2) {
                System.out.println("Invalid campus code.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid campus code format.");
            return null;
        }
        return CollegeChecking.Campus.values()[campusCode];
    }

    /**
     * Create an instance of account based on the command and return it
     * @param accountType
     * @param holder
     * @return account
     */
    private Account getTotalAccount(String accountType, Profile holder) {
        Account account ;
        switch (accountType) {
            case "C":
                account = new Checking(holder, 0);
                break;
            case "CC":
                account = new CollegeChecking(holder, 0, null);
                break;
            case "S":
                account = new Savings(holder, 0,true);
                break;
            case "MM":
                account = new MoneyMarket(holder, 0);
                break;
            default:
                System.out.println("Invalid account type");
                return null;
        }
        return account;
    }

    /**
     * Gets and checks if the date of birth is valid.
     * @param dob
     * @return String of the date if valid, otherwise null
     */
    private Date getDate(String dob) {
        Date dateOfBirth;
        try {
            String[] dobParts = dob.split("/");
            dateOfBirth = new Date(Integer.parseInt(dobParts[1]), Integer.parseInt(dobParts[0]), Integer.parseInt(dobParts[2]));
            if (!dateOfBirth.isValid()){
                System.out.println("DOB invalid: "+dateOfBirth+" not a valid calendar date!");
                return null;
            }
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            if (dateOfBirth.getYear()>year){
                System.out.println("DOB invalid: "+dateOfBirth+" cannot be today or a future day.");
                return null;
            }
            if (dateOfBirth.getYear()==year){
                if (dateOfBirth.getMonth()>month){
                    System.out.println("DOB invalid: "+dateOfBirth+" cannot be today or a future day.");
                    return null;
                }else if (dateOfBirth.getMonth()==month)
                {
                    if (dateOfBirth.getDay()>day){
                        System.out.println("DOB invalid: "+dateOfBirth+" cannot be today or a future day.");
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid date format");
            return null;
        }
        return dateOfBirth;
    }

    /**
     * Get and check if the amount is valid
     * @param tokenizer
     * @param type
     * @return amount if it is positive, otherwise null
     */
    private Double getAmount(StringTokenizer tokenizer,String type) {
        Double amount;
        try {
            amount = Double.parseDouble(tokenizer.nextToken());
            if(amount<=0){
                System.out.println(type + " cannot be 0 or negative.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return null;
        }
        return amount;
    }
}
