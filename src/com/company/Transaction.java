package com.company;


import java.util.Date;

public class Transaction {

    //Beloppet för transaktionen
    private double amount;


    //Tid och datum för transaktion
    private Date timestamp;


    //"memo" för transaktionen, namn för överföringen
    private String memo;


    //kontot för vilken överföringen görs
    private Account inAccount;

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
        return.this.amount;
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
