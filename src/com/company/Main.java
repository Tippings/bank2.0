package com.company;

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

        do{
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

            choice=sc.nextInt();

        }
    }
}
