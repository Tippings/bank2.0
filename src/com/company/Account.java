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

    public Account(String name, User holder, Bank thebank ){

        // set the account name and holder
        this.name = name;
        this.holder = holder;

        // get new account UUID
        this.uuid = thebank.getNewAccountUUID();

        // init transactions
        this.transactions = new ArrayList<Transaction>();


    }
    public String getUUID(){
        return this.uuid;
    }
    public String getSummaryLine(){

        // get the account balance
        double balance = this.getBalance();

        // format the summary line
        if (balance>=0){
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }else{
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);

        }
    }
    // Get the balance of this account by adding the ammounts of the transactions returns the balance value
    public double getBalance(){

        double balance =0;
        for (Transaction t : this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }
    public void printTransHistory(){

        System.out.println("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t >=0; t--){
            System.out.printf(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();

    }
    public void addTransaction(double amount, String memo){

        // create new transaction object and add it to our list
        Transaction newTrans = new  Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }

}
