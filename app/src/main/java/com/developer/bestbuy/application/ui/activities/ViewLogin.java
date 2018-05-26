package com.developer.bestbuy.application.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.EditText;

import com.developer.bestbuy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewLogin extends Activity {

    @BindView(R.id.input_email)
    EditText usernameView;
    @BindView(R.id.input_password) EditText passwordView;
    @BindView(R.id.submit)
    AppCompatButton onLoginClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.submit)
    public void submit() {
        startActivity(new Intent(this, Home.class));
    }
}
