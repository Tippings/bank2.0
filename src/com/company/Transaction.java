package com.company;


import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static com.company.Main.*;

public class Transaction {

    //Beloppet för transaktionen
    //private double amount;


    //Tid och datum för transaktion
    private Date timestamp;


    //"memo" för transaktionen, namn för överföringen
    private String memo;


    //kontot för vilken överföringen görs
//    Isa lägger till:
    private Account inAccount;
    public String fromAccount;
    public String toAccount;
    public double amount;
    public LocalDate date;


//Isa lägger till:
    public Transaction(String fromAccount, String toAccount, double amount, LocalDate date) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.date = date;


    }
    public static void payments(Scanner fr, Scanner acc, ArrayList<Account> accounts, ArrayList<Transaction> buppdrag, File konton) {
        FileToList(fr, buppdrag);
        accountFileToList(acc, accounts);

        for (int i = 0; i < buppdrag.size(); i++) {
            if (buppdrag.get(i).date.isBefore(LocalDate.now()) || buppdrag.get(i).equals(LocalDate.now())) {
                System.out.println(buppdrag.get(i).fromAccount.equals(accounts.get(i).accountNo));//skriver ut betalningsuppdragen på skärmen
                if (buppdrag.get(i).fromAccount.equals(accounts.get(i).accountNo)) {
                    accounts.get(i).accAmount = accounts.get(i).accAmount - buppdrag.get(i).amount;
                }
                for (int j = 0; j < accounts.size(); j++) {
                    if (buppdrag.get(i).toAccount.equals(accounts.get(j).accountNo)) {
                        accounts.get(j).accAmount = accounts.get(j).accAmount + buppdrag.get(i).amount;
                    }
                }
            }
            //todo: nu körs alla betalningsuppdrag om igen när programmet startar om.
        }
        accountListToFile(accounts, konton);
    }



    /**
     * skapa ny transaktion
     * @param amount belopp
     * @param inAccount kontot det gäller
     */
    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date(); //nytt datum-objekt (automatiskt dagens datum
        this.memo = " "; //tom sträng

    }
    /**
     * Skapa ny transaktion
     * @param amount    beloppet som ska överföras
     * @param inAccount kontot överföringen gäller
     *                  memo
     */
    public Transaction(double amount, String memo, Account inAccount) {
        //kalla på two-arg konstruktorn först
        this(amount, inAccount);

        //bestämma memo
        this.memo = memo;

    }

    /**
     * Få fram beloppet på överföringen
     * @return  belopp
     */
    public double getAmount(){
        return this.amount;
    }

    /**
     * Få ut en sträng som summerar transaktionen
     * @return  summa-strängen
     */
    public String getSummaryLine(){

        if(this.amount>=0){
            return String.format("%s : $%.02f : %s", this.timestamp.toString(),
                    this.amount, this.memo);
        } else{
            return String.format("%s : $%.02f : %s", this.timestamp.toString(),
                    this.amount, this.memo);

        }
    }

}
