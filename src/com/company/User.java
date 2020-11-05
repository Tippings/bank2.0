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

    public User ( String firstName, String lastName, String pin, Bank theBank){

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
        this.uuid=theBank.getNewUserUUID();

        // create emty list of accounts
        this.accounts = new ArrayList<Account>();

        // print log message
        System.out.println("New user %s, &s with ID %s created.\n", lastName, firstName, this.uuid);
    }

    public void addAcount(Account anAcct){
        this.accounts.add(anAcct);
    }

    public String getUUID(){
        return this.uuid;
    }

}
