package com.company;

import javax.jws.soap.SOAPBinding;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("ISA");

        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("master mojo");

        User aUser = theBank.addUser("john", "doe", "1234");

        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAcount(newAccount);
        theBank.addAcount(newAccount);

        User curUser;
        while (true) {

            curUser = ATM.mainMenuPromt(theBank, sc);

            ATM.printUserMenu(carUser, sc);

        }
    }

    public static User mainMenuPromt (Bank theBank, Scanner sc){

        String userID;
        String pin;
        User autUser;

        do{
            System.out.println("\n\n Welcome to %s\n\n", theBank.getName());
            System.out.println("Enter user ID:");
            userId = sc.nextLine();
            System.out.println("Enter pin:");
            pin = sc.nextLine();

            authUser=theBank.userLogin(userID, pin);
            if (authUser == null){
                System.out.println("Incorrect");
            }

        }while (authUser == null);

        return authUser;
    }
    public static void printUserMenu (User theUser, Scanner sc){

        theUser.printAccountSummary();

        int choice;

        do {
            System.out.println("Welcome %s, What to do?", theUser.getFirstName());
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

            switch (choice){

                case 1:
                    ATM.showTransHistory(theUser, sc);
                    break;
                case 2:
                    ATM.withdrawFunds(theUser, sc);
                    break;
                case 3:
                    ATM.depositFunds(theUser, sc);
                    break;
                case 4:
                    ATM.tranferFunds(theUser, sc);
                    break;
            }

            // redisplay meny unless the user wants to quit
            if ( choice !=5){
                ATM.printUserMenu(theUser, sc);
            }

        }


    public static void showTransHistory(User theUser, Scanner sc ){

        int theAcct;
        // get account whose transactions history to look at
        do{
            System.out.printf("Enter the number (1-%d) of the account \n" +
                    "whose transactions you want to see:",
                    theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct <0 || theAcct >= theUser.numAccounts()){
                System.out.println("invalid account");
            }

        }while (theAcct <0 || theAcct >= theUser.numAccounts());

        // print the transaction history
        theUser.printAcctTransHistory(theAcct);
    }
    public static void tranferFunds (User theUser, Scanner sc){

        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the account to transfer from
        do{
            System.out.printf("enter the number (1-%d) of the account \n " + "to transfer from: ");
            fromAcct=sc.nextInt()-1;
            if (fromAcct<0 || fromAcct >= theUser.numAccounts()){
                System.out.println("invalid account");
            }
        }while (fromAcct <0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // get the accout to transfer to
        do{
            System.out.printf("enter the number (1-%d) of the account \n " + "to transfer to: ");
            toAcct=sc.nextInt()-1;
            if (toAcct<0 || toAcct >= theUser.numAccounts()){
                System.out.println("invalid account");
            }
        }while (toAcct <0 || toAcct >= theUser.numAccounts());

        // get the ammount to tranfer
        do{
            System.out.printf("enter the ammount to tranfer (max $%.02f) : $",acctBal);
            amount = sc.nextDouble();
            if (amount <0){
                System.out.println("Amount must be greater than zero");
            }else if (amount > acctBal){
                System.out.printf("amount must not be greater than \n" + "balance of $%.02f.\n", acctBal);
            }

        }while (amount<0 || amount > acctBal);
        // finally do the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
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
        do{
            System.out.printf("enter the number (1-%d) of the account \n " + "to transfer from: ");
            fromAcct=sc.nextInt()-1;
            if (fromAcct<0 || fromAcct >= theUser.numAccounts()){
                System.out.println("invalid account");
            }
        }while (fromAcct <0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        // get the ammount to tranfer
        do{
            System.out.printf("enter the ammount to tranfer (max $%.02f) : $",acctBal);
            amount = sc.nextDouble();
            if (amount <0){
                System.out.println("Amount must be greater than zero");
            }else if (amount > acctBal){
                System.out.printf("amount must not be greater than \n" + "balance of $%.02f.\n", acctBal);
            }

        }while (amount<0 || amount > acctBal);
        // gobble up rest of previous input
        sc.nextLine();

        // get the memo
        System.out.println("Enter a memo:");
        memo = sc.nextLine();

        // do the withdraw
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);

    }

    public static void depositFunds(User theUser, Scanner sc){

        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do{
            System.out.printf("enter the number (1-%d) of the account \n " + "to transfer from: ", theUser.numAccounts());
            toAcct=sc.nextInt()-1;
            if (toAcct<0 || toAcct >= theUser.numAccounts()){
                System.out.println("invalid account");
            }
        }while (toAcct <0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);
        // get the ammount to tranfer
        do{
            System.out.printf("enter the ammount to tranfer (max $%.02f) : $",acctBal);
            amount = sc.nextDouble();
            if (amount <0){
                System.out.println("Amount must be greater than zero");
            }else if (amount > acctBal){
                System.out.printf("amount must not be greater than \n" + "balance of $%.02f.\n", acctBal);
            }

        }while (amount<0 || amount > acctBal);
        // gobble up rest of previous input
        sc.nextLine();

        // get the memo
        System.out.println("Enter a memo:");
        memo = sc.nextLine();

        // do the withdraw
        theUser.addAcctTransaction(toAcct, amount, memo);
    }


}
