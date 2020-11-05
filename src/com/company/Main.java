package com.company;

public class Main {

    public static void main(String[] args) {

        System.out.println("ISA");
	// initiat scanner
        Scanner sc= new Scanner(System.in);

        //initiera bank:
        Bank theBank = new Bank("Sanna och Isas fantastiska bank");

        //lägg till user + skapa sparkonto
        User aUser = theBank.addUser("Jane","Doe","1234");

        //lägg till standardbankkonto:
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);
        //minut 54:17 skapar meny i main
        
        
    }

}
