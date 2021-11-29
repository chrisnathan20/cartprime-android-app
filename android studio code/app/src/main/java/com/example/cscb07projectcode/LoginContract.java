package com.example.cscb07projectcode;

public interface LoginContract {
    public interface Model{
        public boolean userExists(String username);
        public boolean correctPassword(String username, String password);
    }

    public interface View {
        public String getUsername();
        public String getPassword();
        public void displayMessage(String message);
        public void onSuccess(String username);
    }

    public interface Presenter {
        public void checkLogin();
    }
}
