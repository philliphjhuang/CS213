package com.example.javafx_bank.controller;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.example.javafx_bank.incident.Account;
import com.example.javafx_bank.incident.Savings;
import com.example.javafx_bank.incident.Checking;
import com.example.javafx_bank.incident.CollegeChecking;
import com.example.javafx_bank.incident.MoneyMarket;
import com.example.javafx_bank.incident.AccountDatabase;
import com.example.javafx_bank.incident.Profile;
import com.example.javafx_bank.incident.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TransactionManagerController {
    @FXML
    private TextArea rightText = new TextArea();
    private final Stage stage = new Stage();
    private String outputText;
    private final AccountDatabase accountDatabase = new AccountDatabase();
    private boolean loyal;
    private CollegeChecking.Campus cSelected = CollegeChecking.Campus.NEW_BRUNSWICK;
    private String message = "";
    private String atSelected = "C";

    /**
     * Creates a window that prompts the user for first name, last name, dob, bank account type, initial deposit,
     * open/close button, and an output text. If the open button is pressed, then all the prompted textFields
     * will be used as information to open an account and add to account database. If the close button is pressed,
     * then only the variable first name, last name, dob, and bank account type will be considered to close the
     * account.
     *
     * @param event
     * @throws IOException
     */
    public void openClose(ActionEvent event) throws IOException {
        Text fnT=new Text("First Name: ");Text lnT=new Text("Last Name: ");Text dobT=new Text("Date of birth: ");
        Text atT=new Text("Account type: ");Text selectC=new Text("Select campus: ");Text loyaltyText=new Text("Select loyalty: ");
        Text idT=new Text("Initial Deposit ($): ");TextArea tA = new TextArea();tA.setEditable(false);
        TextField fnTF=new TextField();TextField lnTF=new TextField();TextField idTF=new TextField();
        VBox vbox=new VBox();vbox.setPadding(new Insets(10, 50, 50, 50));vbox.setSpacing(10);
        HBox dobBox=new HBox();HBox fnBox=new HBox();HBox lnBox=new HBox();HBox cBox=new HBox();HBox ccCampus=new HBox();
        HBox sBox=new HBox();HBox loyalty=new HBox();HBox idBox=new HBox();HBox bBox=new HBox();
        RadioButton cButton=new RadioButton("Checking            ");RadioButton ccButton=new RadioButton("College Checking");
        RadioButton mmButton=new RadioButton("Money Market   ");RadioButton sButton=new RadioButton("Savings");
        RadioButton nb=new RadioButton("New Brunswick  ");RadioButton n=new RadioButton("Newark  ");RadioButton c=new RadioButton("Camden");
        RadioButton lButton=new RadioButton("Loyal Customer  ");RadioButton nlButton=new RadioButton("Not loyal customer");
        ToggleGroup allGroup=new ToggleGroup();ToggleGroup lGroup=new ToggleGroup();ToggleGroup campuses=new ToggleGroup();
        Button open=new Button("Open");Button close=new Button("Close");DatePicker dobP=new DatePicker();atT.setStyle("-fx-font-size: 12pt;");
        open.setPrefSize(100,50);close.setPrefSize(100,50);cButton.setSelected(true);ccCampus.setVisible(false);loyalty.setVisible(false);
        fnT.setStyle("-fx-font-size:12pt;");fnTF.setPromptText("Enter First Name");lnT.setStyle("-fx-font-size:12pt;");lnTF.setPromptText("Enter Last Name");
        dobT.setStyle("-fx-font-size: 12pt;");dobP.setEditable(false);dobP.setPromptText("MM/DD/YYYY");idT.setStyle("-fx-font-size: 12pt;");idTF.setPromptText("Enter an amount");
        cButton.setToggleGroup(allGroup);ccButton.setToggleGroup(allGroup);mmButton.setToggleGroup(allGroup);sButton.setToggleGroup(allGroup);
        nb.setToggleGroup(campuses);n.setToggleGroup(campuses);c.setToggleGroup(campuses);lButton.setToggleGroup(lGroup);nlButton.setToggleGroup(lGroup);
        fnTF.textProperty().addListener((obs,oldV,newV)->fnTF.setText(newV));lnTF.textProperty().addListener((obs,oldV,newV)->lnTF.setText(newV));
        idTF.textProperty().addListener((obs,oldV,newV)->idTF.setText(newV));
        fnBox.getChildren().addAll(fnT,fnTF);vbox.getChildren().add(fnBox);lnBox.getChildren().addAll(lnT,lnTF);vbox.getChildren().add(lnBox);
        dobBox.getChildren().addAll(dobT, dobP);vbox.getChildren().add(dobBox);cBox.getChildren().addAll(cButton, ccButton);
        ccCampus.getChildren().addAll(selectC,nb,n,c);sBox.getChildren().addAll(mmButton,sButton);loyalty.getChildren().addAll(loyaltyText,lButton,nlButton);
        vbox.getChildren().addAll(atT,cBox,ccCampus,sBox,loyalty);idBox.getChildren().addAll(idT,idTF);vbox.getChildren().add(idBox);
        nb.setOnAction(e->cSelected=CollegeChecking.Campus.NEW_BRUNSWICK);n.setOnAction(e->cSelected=CollegeChecking.Campus.NEWARK);c.setOnAction(e->cSelected=CollegeChecking.Campus.CAMDEN);
        lButton.setOnAction(event1->loyal=true);nlButton.setOnAction(event1->loyal=false);
        cButton.setOnAction(e->{ccCampus.setVisible(false);cSelected=null;loyalty.setVisible(false);atSelected="C";});
        ccButton.setOnAction(e->{ccCampus.setVisible(ccButton.isSelected());atSelected="CC";cSelected=CollegeChecking.Campus.NEW_BRUNSWICK;nb.setSelected(true);loyalty.setVisible(false);});
        mmButton.setOnAction(e->{lButton.setSelected(true);loyalty.setVisible(false);ccCampus.setVisible(false);atSelected="MM";cSelected=null;});
        sButton.setOnAction(event1->{loyalty.setVisible(sButton.isSelected());atSelected="S";lButton.setSelected(true);ccCampus.setVisible(false);cSelected=null;loyal=true;});
        open.setOnAction(event1 -> {LocalDate selectedD = null;Date date = null;String input ="";
            try {
                selectedD=dobP.getValue();date=new Date(selectedD.getDayOfMonth(),selectedD.getMonthValue(),selectedD.getYear());
                if(!fnTF.getText().isEmpty()&&!lnTF.getText().isEmpty()&&!date.toString().isEmpty()&&!idTF.getText().isEmpty()){
                    input = atSelected + " " + fnTF.getText() + " " + lnTF.getText() + " " + date.toString() + " " + idTF.getText();
                }
            } catch (NullPointerException e) {
                outputText=handleOpenAccount(new StringTokenizer("", " "));tA.setText(outputText);rightText.appendText(outputText+"\n");return;
            }
            if(sButton.isSelected()&&lButton.isSelected()){input += " 1";}else if(sButton.isSelected()&&nlButton.isSelected()){input += " 0";}
            if(ccButton.isSelected()&&nb.isSelected()){input+=" 0";}
            else if(ccButton.isSelected()&&n.isSelected()){input+=" 1";}else if(ccButton.isSelected()&&c.isSelected()){input+=" 2";}
            outputText = handleOpenAccount(new StringTokenizer(input, " "));tA.setText(outputText);rightText.appendText(outputText + "\n");
        });
        close.setOnAction(event1->{LocalDate selectedD=null;Date date=null;String input="";
            try {
                selectedD=dobP.getValue();date=new Date(selectedD.getDayOfMonth(),selectedD.getMonthValue(),selectedD.getYear());
                if(!fnTF.getText().isEmpty()&&!lnTF.getText().isEmpty()&&!date.toString().isEmpty()){
                    input=atSelected+" "+fnTF.getText()+" "+lnTF.getText()+" "+date.toString()+" ";
                }
            } catch (NullPointerException e) {
                outputText=handleCloseAccount(new StringTokenizer("", " "));tA.setText(outputText);rightText.appendText(outputText + "\n");return;
            }
            outputText=handleCloseAccount(new StringTokenizer(input, " "));tA.setText(outputText);rightText.appendText(outputText + "\n");
        });
        bBox.setAlignment(Pos.CENTER);bBox.setSpacing(10);bBox.getChildren().addAll(open,close);vbox.getChildren().addAll(bBox,new Text("Current output"),tA);
        stage.setScene(new Scene(vbox, 750, 450));stage.setTitle("Open/Close");stage.setResizable(false);stage.show();
    }

    /**
     * Creates a window that prompts the user for first name, last name, dob, bank account type, amount of money
     * deposit/withdraw button, and an output text. If the deposit button is pressed, then the corresponding
     * account will increase its balance based on amount. If the withdraw button is pressed, then the corresponding
     * account will decrease its balance based on amount.
     *
     * @param event
     * @throws IOException
     */
    public void depositWithdraw(ActionEvent event) throws IOException {
        VBox vbox = new VBox();vbox.setPadding(new Insets(10, 50, 50, 50));vbox.setSpacing(10);
        TextArea textArea = new TextArea();textArea.setEditable(false);
        Text fnT=new Text("First Name: ");Text lnT=new Text("Last Name: ");Text dobT=new Text("Date of birth: ");Text atT=new Text("Account type: ");Text amountText=new Text("Amount: ");
        TextField fnTF= new TextField();TextField lnTF = new TextField();TextField aTF=new TextField();
        HBox fnBox=new HBox();HBox lnBox=new HBox();HBox dobBox=new HBox();HBox cBox=new HBox();HBox sBox=new HBox();HBox bBox=new HBox();HBox aBox=new HBox();DatePicker dobP=new DatePicker();
        RadioButton cButton=new RadioButton("Checking              ");RadioButton ccButton=new RadioButton("College Checking");
        RadioButton mmButton = new RadioButton("Money Market     ");RadioButton sButton = new RadioButton("Savings");
        ToggleGroup tg=new ToggleGroup();Button deposit = new Button("Deposit"); Button withdraw = new Button("Withdraw");
        withdraw.setPrefSize(100, 50);deposit.setPrefSize(100, 50);aTF.setPrefWidth(400);
        fnTF.setPromptText("Enter First Name");lnTF.setPromptText("Enter Last Name");dobP.setEditable(false);dobP.setPromptText("MM/DD/YYYY");aTF.setPromptText("Enter amount");
        fnTF.textProperty().addListener((obs,oldV,newV)->fnTF.setText(newV));cButton.setSelected(true);
        fnBox.getChildren().addAll(fnT, fnTF);vbox.getChildren().add(fnBox);lnTF.textProperty().addListener((obs,oldV,newV)->lnTF.setText(newV));
        lnBox.getChildren().addAll(lnT, lnTF);vbox.getChildren().add(lnBox);dobBox.getChildren().addAll(dobT,dobP);vbox.getChildren().add(dobBox);
        cBox.getChildren().addAll(cButton, ccButton);sBox.getChildren().addAll(mmButton, sButton);
        cButton.setToggleGroup(tg);ccButton.setToggleGroup(tg);sButton.setToggleGroup(tg);mmButton.setToggleGroup(tg);
        vbox.getChildren().addAll(atT, cBox, sBox);cButton.setOnAction(event1->atSelected="C");ccButton.setOnAction(event1->atSelected="CC");
        mmButton.setOnAction(event1->atSelected = "MM");sButton.setOnAction(event1 -> atSelected = "S");
        aTF.textProperty().addListener((obs,oldV,newV)->aTF.setText(newV));aBox.getChildren().addAll(amountText,aTF);vbox.getChildren().add(aBox);
        deposit.setOnAction(event1 ->{LocalDate selectedD = null;Date date = null;String input="";
            try {
                selectedD = dobP.getValue();
                date = new Date(selectedD.getDayOfMonth(), selectedD.getMonthValue(), selectedD.getYear());
                if(!fnTF.getText().isEmpty()&&!lnTF.getText().isEmpty()&&!date.toString().isEmpty()&&!aTF.getText().isEmpty()){input=atSelected+" "+fnTF.getText()+" "+lnTF.getText()+" "+date.toString()+" "+aTF.getText();}
            } catch (NullPointerException e) {
                outputText = handleDeposit(new StringTokenizer("", " "));textArea.setText(outputText);
                rightText.appendText(outputText + "\n");return;
            }
            outputText=handleDeposit(new StringTokenizer(input," "));textArea.setText(outputText);rightText.appendText(outputText+"\n");
        });
        withdraw.setOnAction(event1 -> {LocalDate selectedD=null;Date date=null;String input="";
            try {
                selectedD=dobP.getValue();date=new Date(selectedD.getDayOfMonth(),selectedD.getMonthValue(),selectedD.getYear());
                if(!fnTF.getText().isEmpty()&&!lnTF.getText().isEmpty()&&!date.toString().isEmpty()&&!aTF.getText().isEmpty()){input=atSelected+" "+fnTF.getText()+" "+lnTF.getText()+" "+date.toString()+" "+aTF.getText();}
            } catch (NullPointerException e) {
                outputText=handleWithdraw(new StringTokenizer("", " "));textArea.setText(outputText);
                rightText.appendText(outputText + "\n");return;
            }
            outputText=handleWithdraw(new StringTokenizer(input," "));textArea.setText(outputText);rightText.appendText(outputText+"\n");
        });
        bBox.setAlignment(Pos.CENTER);bBox.setSpacing(10);bBox.getChildren().addAll(deposit,withdraw);vbox.getChildren().addAll(bBox,new Text("Current output"),textArea);
        stage.setScene(new Scene(vbox,750,450));stage.setTitle("Deposit/Withdraw");stage.setResizable(false);stage.show();
    }

    /**
     * Creates a window shows four buttons that prints all accounts, imports a file, prints all account's interests
     * and fees, or updates all accounts' interests and fees.
     *
     * @param event
     * @throws IOException
     */
    public void accountDatabaseTab(ActionEvent event) throws IOException {
        TextArea textArea = new TextArea();textArea.setEditable(false);
        VBox vbox = new VBox();vbox.setPadding(new Insets(10, 50, 50, 50));vbox.setSpacing(10);vbox.setAlignment(Pos.CENTER);
        Button printAllAccountsButton = new Button("Print All Accounts");Button importFileButton = new Button("Import File");
        Button printIFButton = new Button("Print Interests and Fees");Button updateIFButton = new Button("Update Accounts with Interests and Fees");
        printAllAccountsButton.setPrefSize(300, 50);importFileButton.setPrefSize(300, 50);
        printIFButton.setPrefSize(300, 50);updateIFButton.setPrefSize(300, 50);
        HBox buttonBox1=new HBox();HBox buttonBox2=new HBox();buttonBox1.setSpacing(10);buttonBox2.setSpacing(10);
        buttonBox1.getChildren().addAll(printAllAccountsButton,importFileButton);buttonBox2.getChildren().addAll(printIFButton, updateIFButton);
        vbox.getChildren().addAll(buttonBox1,buttonBox2);buttonBox1.setAlignment(Pos.CENTER);buttonBox2.setAlignment(Pos.CENTER);
        printAllAccountsButton.setOnAction(event1 ->{outputText = accountDatabase.printSorted();textArea.setText(outputText);rightText.appendText("\n" + outputText);});
        importFileButton.setOnAction(event1 -> {StringBuffer sb=new StringBuffer();FileChooser fileChooser=new FileChooser();outputText="";
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            try {File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    outputText = "File selected: " + selectedFile.getName() + "\n";
                    textArea.setText(outputText);
                    rightText.appendText("\n" + outputText);
                    try {Scanner scanner = new Scanner(selectedFile);
                        while (scanner.hasNextLine()) {
                            String commandLine = scanner.nextLine().trim();
                            if (commandLine.contains(",")) commandLine = "O " + commandLine.replace(",", " ");
                            if (commandLine.isEmpty()) continue;
                            String execute = execute(commandLine);sb.append(execute + "\n");
                            if (execute.equals("Transaction Manager is terminated.")) break;
                        }
                    } catch (IOException e) {
                        sb.append("Error while reading a file");
                    }
                }
            } catch (Exception ex) {
                sb.append("Error while selecting a file: " + ex.getMessage());
            }
            textArea.setText(sb.toString());rightText.appendText(sb.toString());
        });
        printIFButton.setOnAction(e->{outputText=accountDatabase.printFeesAndInterests();textArea.setText(outputText);rightText.appendText("\n"+outputText);});
        updateIFButton.setOnAction(e->{outputText=accountDatabase.printUpdatedBalances();textArea.setText(outputText);rightText.appendText("\n"+outputText);});
        vbox.getChildren().addAll(new Text("Current output"),textArea);
        stage.setScene(new Scene(vbox, 750, 450));stage.setTitle("Account Database");stage.setResizable(false);stage.show();
    }

    /**
     * Creates a window that shows two menu bars. When the menu bar Account is pressed, it will show four items:
     * Open/Close, Deposit/Withdraw, Load from File, and Print All, and they will do its corresponding functions.
     * When the Interests and Fees menu bar is pressed, it will show two items which are to print all accounts'
     * interests and fees and update all accounts' interests and fees.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void menuBar(ActionEvent event) throws IOException {
        VBox vbox = new VBox();vbox.setPadding(new Insets(10, 50, 50, 50));vbox.setSpacing(10);
        TextArea textArea = new TextArea();textArea.setEditable(false);MenuBar menuBar = new MenuBar();
        Menu accountMenu = new Menu("Account");Menu interestsFeesMenu = new Menu("Interests and Fees");
        MenuItem openCloseItem = new MenuItem("Open/Close");MenuItem depositWithdrawItem = new MenuItem("Deposit/Withdraw");
        MenuItem loadFromFileItem = new MenuItem("Load from file");
        openCloseItem.setOnAction(e1->{try {openClose(e1);} catch (IOException e) {throw new RuntimeException(e);}});
        depositWithdrawItem.setOnAction(e1->{try{depositWithdraw(e1);}catch(IOException e){throw new RuntimeException(e);}});
        loadFromFileItem.setOnAction(event1 -> {StringBuffer sb = new StringBuffer();FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));outputText = "";
            try {File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    outputText="File selected: " + selectedFile.getName() + "\n";textArea.setText(outputText);rightText.appendText("\n"+outputText);
                    try {Scanner scanner = new Scanner(selectedFile);
                        while (scanner.hasNextLine()) {
                            String commandLine = scanner.nextLine().trim();
                            if (commandLine.contains(",")) commandLine = "O " + commandLine.replace(",", " ");
                            if (commandLine.isEmpty()) continue;
                            String execute = execute(commandLine);sb.append(execute + "\n");
                            if (execute.equals("Transaction Manager is terminated.")) break;
                        }
                    } catch (IOException e) {
                        sb.append("Error while reading a file");
                    }
                }
            } catch (Exception ex) {
                sb.append("Error while selecting a file: " + ex.getMessage());
            }
            textArea.setText(sb.toString());rightText.appendText(sb.toString());
        });
        MenuItem printAllItem=new MenuItem("Print all");MenuItem printIF=new MenuItem("Print interests and fees");MenuItem updateIF=new MenuItem("Update interests and fees");
        printAllItem.setOnAction(e->{outputText=accountDatabase.printSorted();textArea.setText(outputText);rightText.appendText("\n"+outputText);});
        printIF.setOnAction(e->{outputText=accountDatabase.printFeesAndInterests();textArea.setText(outputText);rightText.appendText("\n"+outputText);});
        updateIF.setOnAction(e->{outputText=accountDatabase.printUpdatedBalances();textArea.setText(outputText);rightText.appendText("\n"+outputText);});
        accountMenu.getItems().addAll(openCloseItem, depositWithdrawItem, loadFromFileItem, printAllItem);interestsFeesMenu.getItems().addAll(printIF, updateIF);
        menuBar.getMenus().addAll(accountMenu, interestsFeesMenu);Label space = new Label(" ");space.setPrefHeight(100);
        vbox.getChildren().add(menuBar);vbox.getChildren().addAll(space, new Text("Current output"), textArea);
        stage.setScene(new Scene(vbox, 750, 450));stage.setTitle("Menu Bar");stage.setResizable(false);stage.show();
    }

    /**
     * Extracts the command from input and execute the corresponding command
     * @param commandLine
     * @return message
     */
    private String execute(String commandLine) {
        StringTokenizer tokenizer = new StringTokenizer(commandLine);
        String command = tokenizer.nextToken();
        switch (command) {
            case "O":
                message = handleOpenAccount(tokenizer);
                break;
            case "C":
                message = handleCloseAccount(tokenizer);
                break;
            case "D":
                message = handleDeposit(tokenizer);
                break;
            case "W":
                message = handleWithdraw(tokenizer);
                break;
            case "P":
                message = accountDatabase.printSorted();
                break;
            case "PI":
                message = accountDatabase.printFeesAndInterests();
                break;
            case "UB":
                message = accountDatabase.printUpdatedBalances();
                break;
            case "Q":
                message = "Transaction Manager is terminated.";
                break;
            default:
                message = "Invalid command!";
                break;
        }
        return message;
    }


    /**
     * Creates and returns an instance of the account (Checking, CollegeChecking, Savings, or Money Market)
     * based on tokenizer, account type, initial deposit, and account holder profile.
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
                if (campus == null) return null; account = new CollegeChecking(holder, initialDeposit, campus);
                break;
            case "S":
                if (tokenizer.countTokens() < 1) {
                    outputText = "Missing loyalty status for savings account.";
                    return null;
                }
                int isLoyal;
                try {
                    isLoyal = Integer.parseInt(tokenizer.nextToken());
                    if (isLoyal < 0 || isLoyal > 1) {
                        outputText = "Invalid loyalty status.";
                        return null;
                    }
                } catch (NumberFormatException e) {
                    outputText = "Invalid loyalty status format";
                    return null;
                }
                account = new Savings(holder, initialDeposit, isLoyal == 1);
                break;
            case "MM":
                if (initialDeposit < 2000) {
                    outputText = "Minimum of $2000 to open a Money Market account.";
                    return null;
                }
                account = new MoneyMarket(holder, initialDeposit);
                break;
            default:
                outputText = "Invalid account type";
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
            outputText = "Missing campus code for college checking account.";
            return null;
        }
        int campusCode;
        try {
            campusCode = Integer.parseInt(tokenizer.nextToken());
            if (campusCode < 0 || campusCode > 2) {
                outputText = "Invalid campus code.";
                return null;
            }
        } catch (NumberFormatException e) {
            outputText = "Invalid campus code format.";
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
        Account account;
        switch (accountType) {
            case "C":
                account = new Checking(holder, 0);
                break;
            case "CC":
                account = new CollegeChecking(holder, 0, null);
                break;
            case "S":
                account = new Savings(holder, 0, true);
                break;
            case "MM":
                account = new MoneyMarket(holder, 0);
                break;
            default:
                outputText = "Invalid account type";
                return null;
        }
        return account;
    }

    /**
     * Get and check if the amount is valid
     * @param tokenizer
     * @param type
     * @return amount if it is positive, otherwise null
     */
    private Double getAmount(StringTokenizer tokenizer, String type) {
        String str = tokenizer.nextToken();;
        Double amount = null;
        try {
            amount = Double.parseDouble(str);
            if(amount==null) {
                outputText = "Missing data for opening an account.";
                return null;
            } else if(amount <= 0) {
                outputText = type + " cannot be 0 or negative.";
                return null;
            }
        } catch (NumberFormatException e) {
            outputText = "Not a valid amount.";
            return null;
        }
        return amount;
    }

    /**
     * Gets and checks if the date of birth is valid.
     * @param dob
     * @return String of the date if valid, otherwise null
     */
    private Date getDate(String dob, StringBuffer isValidString) {
        Date dateOfBirth;
        try {
            if(!dob.contains("/")) {
                isValidString.append("Missing data for opening an account.");
                return null;
            }
            String[] dobParts = dob.split("/");
            dateOfBirth = new Date(Integer.parseInt(dobParts[1]), Integer.parseInt(dobParts[0]),
                    Integer.parseInt(dobParts[2]));
            if(dateOfBirth == null) {
                isValidString.append("Missing data for opening an account.");
                return null;
            }
            if (!dateOfBirth.isValid()) {
                isValidString.append("DOB invalid: " + dateOfBirth + " not a valid calendar date!");
                return null;
            }
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            if (dateOfBirth.getYear() > year) {
                isValidString.append("DOB invalid: " + dateOfBirth + " cannot be today or a future day.");
                return null;
            }
            if (dateOfBirth.getYear() == year) {
                if (dateOfBirth.getMonth() > month) {
                    isValidString.append("DOB invalid: " + dateOfBirth + " cannot be today or a future day.");
                    return null;
                } else if (dateOfBirth.getMonth() == month) {
                    if (dateOfBirth.getDay() >= day) {
                        isValidString.append("DOB invalid: " + dateOfBirth + " cannot be today or a future day.");
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            isValidString.append("Invalid date format");
            return null;
        }
        return dateOfBirth;
    }


    /**
     * Handles an opening account based on the information in the tokenizer.
     * @param tokenizer a StringTokenizer with account opening data
     */
    private String handleOpenAccount(StringTokenizer tokenizer) {
        StringBuffer sb = new StringBuffer();Calendar today = Calendar.getInstance();
        if (tokenizer.countTokens() < 5) return "Missing data for opening an account.";
        String accountType=tokenizer.nextToken();if(accountType == null)return "Missing data for opening an account.";
        String firstName = tokenizer.nextToken(), lastName = tokenizer.nextToken(),dob = tokenizer.nextToken();
        if (firstName == null || lastName == null) return "Missing data for opening an account.";
        Double initialDeposit=getAmount(tokenizer,"Initial deposit");
        if(initialDeposit==null)return outputText;
        Date dateOfBirth=getDate(dob,sb);
        if(dateOfBirth == null){outputText=sb.toString();return outputText;}
        int age = today.get(Calendar.YEAR) - dateOfBirth.getYear();
        if (dateOfBirth.getMonth() > (today.get(Calendar.MONTH) + 1) || (dateOfBirth.getMonth() ==
                (today.get(Calendar.MONTH) + 1) && dateOfBirth.getDay() > today.get(Calendar.DAY_OF_MONTH))) {age--;}
        if (age < 16) {return "DOB invalid: " + dateOfBirth + " under 16.";}
        else if(accountType.equals("CC") && age >= 24) {return "DOB invalid: " + dateOfBirth + " over 24.";}
        Profile holder = new Profile(firstName, lastName, dateOfBirth);
        Account a1 = getAccount(tokenizer, accountType, initialDeposit, holder), a2=null, a3=null, a4=null;
        if (a1 == null) return outputText;
        if ("C".equals(accountType)){
            a2=new CollegeChecking(a1.getHolder(),0,null);a3=new MoneyMarket(a1.getHolder(),0);
            a4=new Savings(a1.getHolder(),0, true);
        } else if ("CC".equals(accountType)){
            a2=new Checking(a1.getHolder(),0);a3=new MoneyMarket(a1.getHolder(),0);
            a4=new Savings(a1.getHolder(),0,true);
        } else if ("MM".equals(accountType)) {
            a2=new CollegeChecking(a1.getHolder(),0,null);a3=new Checking(a1.getHolder(),0);
            a4=new Savings(a1.getHolder(),0, true);
        } else if ("S".equals(accountType)){
            a2=new CollegeChecking(a1.getHolder(),0,null);a3=new MoneyMarket(a1.getHolder(),0);
            a4=new Checking(a1.getHolder(),0);
        }
        if((a2==null&&a3==null&&a4==null)||(!accountDatabase.contains(a4)&&!accountDatabase.contains(a3)&&
                !accountDatabase.contains(a2)&&!accountDatabase.contains(a1))) {
            if(accountDatabase.open(a1)) {
                return a1 + " opened.";
            } else {
                return a1 + " is already in the database.";
            }
        } else {
            return a1 + " is already in the database.";
        }
    }

    /**
     * Handles a closing account based on the information in the tokenizer.
     * @param tokenizer a StringTokenizer with account closure data
     */
    private String handleCloseAccount(StringTokenizer tokenizer) {
        StringBuffer isValidString = new StringBuffer();
        if (tokenizer.countTokens() < 4) return "Missing data for closing an account.";
        String type = tokenizer.nextToken();
        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        String dob = tokenizer.nextToken();
        Date dateOfBirth = getDate(dob, isValidString);
        if (dob == null) return "dateOfBirth not null.";
        if (dateOfBirth == null) {
            outputText = isValidString.toString();
            return isValidString.toString();
        }
        Profile holder = new Profile(firstName, lastName, dateOfBirth);
        Account account = getTotalAccount(type, holder);
        if (account == null) return "account not null.";
        if (accountDatabase.close(account)) {
            return account.toString() + " has been closed.";
        } else {
            return account.toString() + " is not in the database.";
        }
    }

    /**
     * Handles the deposit process of an account.
     * @param tokenizer
     */
    private String handleDeposit(StringTokenizer tokenizer) {
        StringBuffer sb = new StringBuffer();
        if (tokenizer.countTokens() < 5) return "Invalid data for deposit";
        String accountType = tokenizer.nextToken();
        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        String dob = tokenizer.nextToken();
        Double amount = getAmount(tokenizer, "Deposit - amount");
        if (amount == null) return outputText;
        if (dob == null) return "dateOfBirth not null.";
        Date dateOfBirth = getDate(dob, sb);
        if (dateOfBirth == null) {
            outputText = sb.toString();
            return sb.toString();
        }
        Profile holder = new Profile(firstName, lastName, dateOfBirth);
        Account account = getTotalAccount(accountType, holder);
        if (account == null) return "account not null.";
        if (accountDatabase.deposit(account, amount)) {
            return account.toString() + " Deposit - balance updated.";
        } else {
            return account.toString() + " is not in the database.";
        }
    }

    /**
     * Handles the withdrawal process of an account.
     * @param tokenizer
     */
    private String handleWithdraw(StringTokenizer tokenizer) {
        StringBuffer sb = new StringBuffer();
        if (tokenizer.countTokens() < 5) return "Invalid data for withdrawal";
        String accountType = tokenizer.nextToken();
        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        String dob = tokenizer.nextToken();
        Double amount = getAmount(tokenizer, "Withdraw - amount");
        if (amount == null) return outputText;
        if (dob == null) return "dateOfBirth not null.";
        Date dateOfBirth = getDate(dob, sb);
        if (dateOfBirth == null) {
            outputText = sb.toString();
            return sb.toString();
        }
        Profile holder = new Profile(firstName, lastName, dateOfBirth);
        Account account = getTotalAccount(accountType, holder);
        if (account == null) return "account not null.";
        if (accountDatabase.withdraw(account, amount) == 3) {
            return account + " Withdraw - balance updated.";
        } else if (accountDatabase.withdraw(account, amount) == 2) {
            return account + " Withdraw - insufficient fund.";
        } else if (accountDatabase.withdraw(account, amount) == 1) {
            return account + " is not in the database.";
        }
        return " not in the database.";
    }
}