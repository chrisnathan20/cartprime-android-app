package com.example.cscb07projectcode;

public class StoreOwner {

    // declare fields for each StoreOwner
    String username;
    String password;
    String firstname;
    String lastname;
    /* TO-DO
     Store store = new Store();
     */

    // empty constructor
    public StoreOwner(){
    }

    // non-empty constructor
    public StoreOwner(String firstname, String lastname, String username, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    // getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    /* TO-DO
    public Store set_store(){
        ;
    }*/
}
