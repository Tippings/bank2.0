package com.company;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static com.company.Transaction.payments;

public class Main {

    public static void main(String[] args) throws IOException {
        // Isa lägger till här överst:

        Random r = new Random(100);
        ArrayList<Transaction> buppdrag = new ArrayList<>();
        LocalDate idag = LocalDate.now();
        File payments = new File("betalningsuppdrag.pwd");
        if (!payments.exists()) payments.createNewFile();
        Scanner fr = new Scanner(payments);

        File konton = new File("konton.pwd");
        if (!konton.exists()) konton.createNewFile();
        Scanner acc = new Scanner(konton);

        ArrayList<Account> accounts = new ArrayList<>();
        payments(fr, acc, accounts, buppdrag, konton);


        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("master mojo");

        User aUser = theBank.addUser("john", "doe", "1234");

        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            curUser = Main.mainMenuPrompt(theBank, sc);

            Main.printUserMenu(curUser, sc,buppdrag,accounts,payments,fr,acc,konton);

        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\n Welcome to %s\n\n", theBank.getName());
            System.out.println("Enter user ID:");
            userID = sc.nextLine();
            System.out.println("Enter pin:");
            pin = sc.nextLine();

            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect");
            }

        } while (authUser == null);

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc,ArrayList<Transaction> buppdrag, ArrayList<Account> accounts, File payments, Scanner fr, Scanner acc, File konton) {

        theUser.printAccountSummary();

        int choice;

        do {
            System.out.printf("Welcome %s, What to do?", theUser.getFirstName());
            System.out.println("[1] Skapa konto");
            System.out.println("[2] Lista konton");
            System.out.println("[3] Sätt in pengar på konto");
            System.out.println("[4] Ta ut pengar från konto");
            System.out.println("[5] Lägg till betalningsuppdrag");
            System.out.println("[6] Ta bort betalningsuppdrag");
            System.out.println("[7] Gör överföring mellan två konton");
            System.out.println("[8] Visa kassavalv");
            System.out.println("[9] Hantera medarbetare");
            System.out.println("[0] Avsluta programmet");

            choice = sc.nextInt();

            if (choice < 0 || choice > 9) {
                System.out.println("Invalid choice");
            }
        } while (choice < 0 || choice > 9);

        switch (choice) {

            case 1:
                Main.showTransHistory(theUser, sc);
                break;
            case 2:
                Main.withdrawFunds(theUser, sc);
                break;
            case 3:
                Main.depositFunds(theUser, sc);
                break;
            case 4:
                Main.tranferFunds(theUser, sc);
                break;
            case 5:
                Main.payFile(buppdrag, accounts, payments, fr, acc, konton,sc);
                break;


        }

        // redisplay meny unless the user wants to quit
        if (choice != 10) {
            Main.printUserMenu(theUser, sc,buppdrag, accounts, payments, fr, acc, konton);
        }

    }


    public static void showTransHistory(User theUser, Scanner sc) {

        int theAcct;
        // get account whose transactions history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account \n" +
                            "whose transactions you want to see:",
                    theUser.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("invalid account");
            }

        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        // print the transaction history
        theUser.printAcctTransHistory(theAcct);
    }

    public static void tranferFunds(User theUser, Scanner sc) {

        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the account to transfer from
        do {
            System.out.printf("enter the number (1-%d) of the account \n " + "to transfer from: ");
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("invalid account");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // get the accout to transfer to
        do {
            System.out.printf("enter the number (1-%d) of the account \n " + "to transfer to: ");
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("invalid account");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        // get the ammount to tranfer
        do {
            System.out.printf("enter the ammount to tranfer (max $%.02f) : $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("amount must not be greater than \n" + "balance of $%.02f.\n", acctBal);
            }

        } while (amount < 0 || amount > acctBal);
        // finally do the transfer
        theUser.addAcctTransaction(fromAcct, -1 * amount, String.format(
                "transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format(
                "transfer to account %s", theUser.getAcctUUID(fromAcct)));
    }


    // process a fund withdraw from acc

    public static void withdrawFunds(User theUser, Scanner sc) {

        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("enter the number (1-%d) of the account \n " + "to transfer from: ");
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("invalid account");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        // get the ammount to tranfer
        do {
            System.out.printf("enter the ammount to tranfer (max $%.02f) : $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("amount must not be greater than \n" + "balance of $%.02f.\n", acctBal);
            }

        } while (amount < 0 || amount > acctBal);
        // gobble up rest of previous input
        sc.nextLine();

        // get the memo
        System.out.println("Enter a memo:");
        memo = sc.nextLine();

        // do the withdraw
        theUser.addAcctTransaction(fromAcct, -1 * amount, memo);

    }

    public static void depositFunds(User theUser, Scanner sc) {

        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("enter the number (1-%d) of the account \n " + "to transfer from: ", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("invalid account");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);
        // get the ammount to tranfer
        do {
            System.out.printf("enter the ammount to tranfer (max $%.02f) : $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("amount must not be greater than \n" + "balance of $%.02f.\n", acctBal);
            }

        } while (amount < 0 || amount > acctBal);
        // gobble up rest of previous input
        sc.nextLine();

        // get the memo
        System.out.println("Enter a memo:");
        memo = sc.nextLine();

        // do the withdraw
        theUser.addAcctTransaction(toAcct, amount, memo);
    }

    //    Isa lägger till :
    
    public static void payFile(ArrayList<Transaction> buppdrag, ArrayList<Account> accounts, File payments, Scanner fr, Scanner acc, File konton, Scanner sc) {
        System.out.println("*** Lägg till betalningsuppdrag***");
        System.out.println("Nytt betalningsuppdrag:");
        while (true) {
            System.out.println("Från konto:");
            String fromAccount = sc.nextLine();
            System.out.println("Till konto:");
            String toAccount = sc.nextLine();
            System.out.println("Belopp: ");
            double amount = Double.parseDouble(sc.nextLine());
            System.out.println("Datum för överföring(YYYY-MM-DD):");
            LocalDate date = LocalDate.parse(sc.nextLine());
            /*if (date.toString().isEmpty()) {//försök hitta något som gör det nu med localDate
                //ge dagens datum om inget annat anges
                date = idag;
            }*/
            Transaction trans = new Transaction(fromAccount, toAccount, amount, date);
            buppdrag.add(trans);
            listToFile(buppdrag, payments);
            FileToList(fr, buppdrag);
            payments(fr, acc, accounts, buppdrag, konton);

        }
    }

    public static void listToFile(ArrayList<Transaction> buppdrag, File payments) {
        try {
            FileWriter fw = new FileWriter(payments);
            for (int i = 0; i < buppdrag.size(); i++) {
                fw.write(buppdrag.get(i).fromAccount + ";" +
                        buppdrag.get(i).toAccount + ";" +
                        buppdrag.get(i).amount + ";" +
                        buppdrag.get(i).date + "\n");
            }
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public static void accountFileToList(Scanner acc, ArrayList<Account> accounts) {
        while (acc.hasNextLine()) {
            String rad = acc.nextLine();
            String[] delar = rad.split(";");
            Account account = new Account(delar[0], Double.parseDouble(delar[1]));
            accounts.add(account);
        }


    }

    public static void accountListToFile(ArrayList<Account> accounts, File konton) {
        try {
            FileWriter fw = new FileWriter(konton);
            for (int i = 0; i < accounts.size(); i++) {
                fw.write(accounts.get(i).accountNo + ";" +
                        accounts.get(i).accAmount +
                        //accounts.get(i).uuid +
                        "\n");
            }
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public static void FileToList(Scanner fr, ArrayList<Transaction> buppdrag) {
        while (fr.hasNextLine()) {
            String rad = fr.nextLine();
            String[] delar = rad.split(";");
            System.out.println(delar[0] + " " + delar[1] + " " + Double.parseDouble(delar[2]) + " " + delar[3]);
            Transaction trans = new Transaction(delar[0], delar[1], Double.parseDouble(delar[2]), LocalDate.parse(delar[3]));
            buppdrag.add(trans);
        }


    }
}



