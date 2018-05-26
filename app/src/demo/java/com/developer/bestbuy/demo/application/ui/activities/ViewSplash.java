package com.developer.bestbuy.demo.application.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.developer.bestbuy.application.ui.activities.ViewLogin;

public class ViewSplash extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, ViewLogin.class));
    }

}