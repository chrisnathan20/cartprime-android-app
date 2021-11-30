package com.example.cscb07projectcode;

import android.content.Intent;
import android.widget.EditText;

public class StoreOwnerPresenter implements LoginContract.Presenter{


    private LoginContract.Model model;
    private LoginContract.View view;

    public StoreOwnerPresenter(LoginContract.Model model, LoginContract.View view){
        this.model = model;
        this.view = view;
    }

    public void validLogin(String username){
        view.displayMessage("Login Successful.");
        view.onSuccess(username);
    }

    public void invalidLogin(){
        view.displayMessage("Incorrect username or password.");
    }

    public void emptyLogin(){
        view.displayMessage("Please fill in all input fields.");
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
            model.userCheck(username, password, this);
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
