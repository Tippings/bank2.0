package com.company;

import java.util.ArrayList;

public class Bank {

    private String name;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    /**
     * skapa nytt bankobjekt med tomma user- och account listor
     * @param name  bankens namn
     */
    public Bank(String name){
        this.name= name;
        this.users= new ArrayList<User>();
        this.accounts = new ArrayList<Account>();

    }

    /**
     * Skapa ett nytt UUID till användare
     *
     * @return uuid
     */
    public String getNewUserUUID() {
        //initiera
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
// fortsätt skapa ny uuid så länge det inte är unikt
        do {
            //generera nummer:
            uuid = "";

            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            //kolla för att försäkra att det är unikt:
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID))==0){
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);


        return uuid;
    }

    public String getNewAccountUUID() {

        //initiera
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;
// fortsätt skapa ny uuid så länge det inte är unikt
        do {
            //generera nummer:
            uuid = "";

            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            //kolla för att försäkra att det är unikt: Loopa genom konton
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID))==0){
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);


        return uuid;
    }

    /**
     * Lägger till ett konto
     *
     * @param anAcct kontot som ska läggas till
     */
    public void addaAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    /**
     * Skapa ny User
     *
     * @param firstName förnamn
     * @param lastName  efternamn
     * @param pin       lösenord
     * @return nytt user-objekt
     */
    public User addUser(String firstName, String lastName, String pin) {
        //Skapa ny userobjekt och lägg till i listan
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        //skapa sparkonto direkt när User registreras
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    /**
     * Ta fram user-objektet med spec. userId och lösenord- returneras om hittas annars null
     *
     * @param userID UUID användaren
     * @param pin    användarens lösen
     * @return user-objekt
     */
    public User usrLogin(String userID, String pin) {
        //returnera user om allt korrekt

        //sök igenom listan med användare:
        for (User u : this.users) {

            //kolla om userID är korrekt:
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {//GÖR VALIDATEPIN I USER-KLASS + messageDigest minut 46:14 i klippet
                return u;
            }
        }
        //om vi inte hittat användare eller lösenord är fel:
        return null;
    }

}



