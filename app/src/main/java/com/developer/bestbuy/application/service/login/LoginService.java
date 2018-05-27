package com.developer.bestbuy.application.service.login;

import android.content.Context;

import com.developer.bestbuy.application.service.ILoginView;

public class LoginService {
    private Context context;
    private ILoginView view;

    public LoginService(Context context, ILoginView view){
        this.context = context;
        this.view = view;
    }

    public boolean login(){
        try{
            if(this.view.getUsername().length()> 0 && this.view.getPassword().length()>0){
                return  true;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }

}
