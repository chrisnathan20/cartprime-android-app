package com.example.cscb07projectcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.cscb07projectcode.Activities.LoginStoreOwnerActivity;
import com.example.cscb07projectcode.Activities.StoreOwnerMainActivity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class PresenterUnitTest {

    @Mock
    LoginStoreOwnerActivity view;

    @Mock
    StoreOwnerModel model;

    @Test
    public void testPresenterEmptyUsernameEmptyPassword(){
        // stubbing
        when(view.getUsername()).thenReturn("");
        when(view.getPassword()).thenReturn("");

        LoginPresenter presenter = new LoginPresenter(model, view);
        presenter.checkLogin();

        //verify correct message is displayed
        verify(view).displayMessage("Please fill in all input fields.");
    }

    @Test
    public void testPresenterNonEmptyUsernameEmptyPassword(){
        // stubbing
        when(view.getUsername()).thenReturn("mockito");
        when(view.getPassword()).thenReturn("");

        LoginPresenter presenter = new LoginPresenter(model, view);
        presenter.checkLogin();

        //verify correct message is displayed
        verify(view).displayMessage("Please fill in all input fields.");
    }

    @Test
    public void testPresenterEmptyUsernameNonEmptyPassword(){
        // stubbing
        when(view.getUsername()).thenReturn("");
        when(view.getPassword()).thenReturn("testing");

        LoginPresenter presenter = new LoginPresenter(model, view);
        presenter.checkLogin();

        //verify correct message is displayed
        verify(view).displayMessage("Please fill in all input fields.");
    }

}