package com.company;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class User {


   // First name user
    private String firstName;

    // Last name user
    private String lastName;

    // The ID number of the user
    private String uuid;

    // The MD5 hash of the users pin
    private byte pinHash[];

    // The list of this users accounts
    private ArrayList<Account>accounts;

    public User ( String firstName, String lastName, String pin, Bank theBank) {

        // set user name
        this.firstName = firstName;
        this.lastName = lastName;


        // store pincode, security issue
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("error");
            e.printStackTrace();
            System.exit(1);
        }

        // get a new unique universal id for the user
        this.uuid = theBank.getNewUserUUID();

        // create emty list of accounts
        this.accounts = new ArrayList<Account>();

        // print log message
        System.out.printf("New user %s, %s with ID %s created.\n",
                lastName, firstName, this.uuid);
    }

    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    public String getUUID(){
        return this.uuid;
    }

    public boolean validatePin(String aPin){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("error");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }
    // return user first name
    public String getFirstName(){
        return this.firstName;
    }
    // prints summaries for the account of this user
    public void printAccountSummary(){
        System.out.printf("\n\n%s's accounts summary", this.firstName);
        for (int a = 0; a < this.accounts.size();a++){
            System.out.printf("%d) %s\n", a+1,
                    this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }
    // get the number of the accounts of the user
    public int numAccounts(){
        return this.accounts.size();
    }
    public void printAcctTransHistory( int acctIdx){
        this.accounts.get(acctIdx).printTransHistory();
    }
    // get the balance of a particular account
    public double getAcctBalance(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();
    }
    // get UUID of a particular acc
    public  String getAcctUUID( int acctIdx){
        return this.accounts.get(acctIdx).getUUID();
    }
    public void addAcctTransaction( int acctIdx, double amount, String memo){
        this.accounts.get(acctIdx).addTransaction(amount,memo);
    }

}
