package com.example.cscb07projectcode;

public interface LoginContract {
    public interface Model{
        public boolean[] userCheck(String username, String password, LoginContract.Presenter presenter);
        public void passwordCheck(String username, String password, LoginContract.Presenter presenter);
    }

    public interface View {
        public String getUsername();
        public String getPassword();
        public void displayErrorMessage(String message);
        public void displaySuccessMessage(String message);
        public void onSuccess(String username);
    }

    public interface Presenter {
        public void checkLogin();

        //String username to be used for intent passed to next activity
        public void validLogin(String username);
        public void invalidLogin();
    }
}
