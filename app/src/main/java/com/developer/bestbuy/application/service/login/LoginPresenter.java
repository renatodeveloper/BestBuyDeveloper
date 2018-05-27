package com.developer.bestbuy.application.service.login;

import android.content.Context;

import com.developer.bestbuy.R;
import com.developer.bestbuy.application.service.ILoginView;

public class LoginPresenter {
    Context context;

    private ILoginView view;
    private LoginService service;

    public LoginPresenter(Context context, ILoginView view){
        this.context = context;
        this.service = new LoginService(context, view);
        this.view = view;
    }


    public void onLoginClicked() {
        try {
            String username = view.getUsername();
            if (username.isEmpty()) {
                view.showUsernameError(R.string.strLyLoginUsername_error);
                return;
            }
            final String password = view.getPassword();
            if (password.isEmpty()) {
                view.showPasswordError(R.string.strLyLoginPassword_error);
                return;
            }

            boolean loginSucceeded = service.login();
            if(loginSucceeded){
                view.startMainActivity();
            }else{
                view.showLoginError(R.string.strLyLoginloginfailed);
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

}
