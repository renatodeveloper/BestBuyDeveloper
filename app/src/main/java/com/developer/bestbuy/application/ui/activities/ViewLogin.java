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
import com.developer.bestbuy.infrastructure.helper.DialogHelper;

import java.util.Random;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_SHORT;

public class ViewLogin extends Activity implements ILoginView {

    private LoginPresenter loginPresenter;
    private ActivityUtil util;
    private DialogHelper helper;

    @BindView(R.id.textViewVS) TextView dsVersao;
    @BindView(R.id.input_email) EditText usernameView;
    @BindView(R.id.input_password) EditText passwordView;
    @BindView(R.id.submit) AppCompatButton onLoginClick;
    @BindView(R.id.progressBar) ProgressBar myProgressBar;
    @BindColor(R.color.color_red) int red;
    //@BindString(R.string.title) String title;
    //@BindDimen(R.dimen.spacer) Float spacer;

    public static  int idUsuarioSimulado;
    public static  int idPedidoSimulado;

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
            util = new ActivityUtil(ViewLogin.this);
            helper = new DialogHelper(ViewLogin.this);
            //util.limpaPref_if_Carro(getApplicationContext());
            //util.limpaPref_if_Pedido(getApplicationContext());
            //util.limpaPref_if_Usuario(getApplicationContext());

            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            dsVersao.setText(pInfo.versionName + pInfo.versionCode);
            dsVersao.setTextColor(red);

            loginPresenter = new LoginPresenter(this, this);

        }catch (Exception e){
            e.getMessage().toString();
        }
    }


    @OnClick(R.id.submit)
    public void submit() {
        try{
            if(!util.isOnline()){
                helper.noConection(ViewLogin.this);
            }else{
                loginPresenter.onLoginClicked();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
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
        try{
            //DADO DE SIMULAÇÃO...
            Random ranUser = new Random();
            idUsuarioSimulado = ranUser.nextInt(100);

            Random ranPed = new Random();
            idPedidoSimulado = ranPed.nextInt(100);

            startActivity(new Intent(this, Home.class));
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
