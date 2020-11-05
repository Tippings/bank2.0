package com.company;


import java.util.ArrayList;

public class Account {

    // the name of the account
    private String name;

    // the account ID number
    private String uuid;

    // user that holds the account
    private User holder;

    // list of transactions for this account
    private ArrayList<Transaction>transactions;

    public Account(String name, User holder, Bank the bank ){

        // set the account name and holder
        this.name = name;
        this.holder = holder;

        // get new account UUID
        thid.uuid = thebank.getNewAccountUUID();

        // init transactions
        this.transactions = new ArrayList<Transaction>();


    }
    public String getUUID(){
        return this.uuid;
    }

}
