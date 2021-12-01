package com.example.cscb07projectcode;

import android.content.Intent;
import android.widget.EditText;

public class LoginPresenter implements LoginContract.Presenter{


    private LoginContract.Model model;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.Model model, LoginContract.View view){
        this.model = model;
        this.view = view;
    }

    public void validLogin(String username){
        view.displaySuccessMessage("Login Successful.");
        view.onSuccess(username);
    }

    public void invalidLogin(){
        view.displayErrorMessage("Incorrect username or password.");
    }

    public void emptyLogin(){
        view.displayErrorMessage("Please fill in all input fields.");
    }

    public void checkLogin(){

        //Gets string values from appropriate editTexts using view method
        String username = view.getUsername();

        String password = view.getPassword();

        //Display appropriate message if any is empty
        if(username.isEmpty() | password.isEmpty()){
            this.emptyLogin();
        }

        //New login check algorithm, everything else is done inside userExists
        else{
            boolean [] for_stubbing = model.userCheck(username, password, this);

            if(for_stubbing[0]){
                if (for_stubbing[1]){
                    validLogin(username);
                }
                else{
                    invalidLogin();
                }
            }
        }

        //Checks if correct username and correct password
        /*
        else if (model.userExists(username)){
            if (model.correctPassword(username, password)) {
                view.onSuccess(username);
            }
            else{
                //change after finish debugging
                view.displayMessage("Correct username but incorrect password.");
            }
        }

        else{
            view.displayMessage("Incorrect username or password.");
        }
         */


    }
}
