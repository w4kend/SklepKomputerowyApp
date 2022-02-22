package org.example;

//klasa singleton dająca możliwość przesyłać dane klienta innym klasom w ramach jego sesji
public class UserSession {
    private User user;
    private final static UserSession INSTANCE = new UserSession();

    private UserSession() {}

    public static UserSession  getInstance() {
        return INSTANCE;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public User getUser() {
        return this.user;
    }

}
