package org.example;


//klasa odwzorowująca użytkownika(klienta)
public class User {

    private int Id_user;
    private String firstName;
    private String surName;
    private String userName;
    private String password;



    public User(int Id_user, String firstName, String surName, String userName, String password) {
        this.Id_user = Id_user;
        this.firstName = firstName;
        this.surName = surName;
        this.userName = userName;
        this.password = password;
    }

    public User() {}

    public int getId_user() {
        return Id_user;
    }

    public void setId_user(int id_user) {
        Id_user = id_user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}