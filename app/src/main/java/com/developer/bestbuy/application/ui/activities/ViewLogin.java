package com.developer.bestbuy.application.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.bestbuy.R;
import com.developer.bestbuy.application.service.ILoginView;
import com.developer.bestbuy.application.service.login.LoginPresenter;
import com.developer.bestbuy.infrastructure.helper.ActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_SHORT;

public class ViewLogin extends Activity implements ILoginView {

    private LoginPresenter loginPresenter;
    public ActivityUtil activityUtil;


    @BindView(R.id.textViewVS) TextView dsVersao;
    @BindView(R.id.input_email) EditText usernameView;
    @BindView(R.id.input_password) EditText passwordView;
    @BindView(R.id.submit) AppCompatButton onLoginClick;
    @BindView(R.id.progressBar) ProgressBar myProgressBar;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_login);
        ButterKnife.bind(this);

        try{
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            dsVersao.setText(pInfo.versionName + pInfo.versionCode);
            dsVersao.setTextColor(Color.RED);

            loginPresenter = new LoginPresenter(this, this);

        }catch (Exception e){
            e.getMessage().toString();
        }
    }


    @OnClick(R.id.submit)
    public void submit() {
        loginPresenter.onLoginClicked();
    }

    @Override
    public String getUsername() {return usernameView.getText().toString();}

    @Override
    public String getPassword() {
        return passwordView.getText().toString();
    }

    @Override
    public void showUsernameError(int resId) {

        usernameView.setError(getString(resId));
    }

    @Override
    public void showPasswordError(int resId) {

        passwordView.setError(getString(resId));
    }

    @Override
    public void showLoginError(int resId) {
        Toast.makeText(this, getString(resId), LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, Home.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
