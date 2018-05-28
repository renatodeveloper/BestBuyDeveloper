package com.developer.bestbuy.application.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.developer.bestbuy.application.service.IDaoModel;
import com.developer.bestbuy.application.service.dao.DaoModelPresenter;
import com.developer.bestbuy.infrastructure.helper.ActivityUtil;

public class ViewSplash extends Activity implements IDaoModel {
    public ActivityUtil activityUtil;
    private DaoModelPresenter daoModelPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            activityUtil = new ActivityUtil();
            activityUtil.limpaPrefJSON_CAR(getApplicationContext());
            activityUtil.limpaPref_if_Pedido(getApplicationContext());
            //activityUtil.limpaPref_if_Usuario(getApplicationContext());

            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1){
                daoModelPresenter = new DaoModelPresenter(this, this);
                daoModelPresenter.createdbInterno();
            }else{
            activityUtil.verifyStoragePermissionsAll(ViewSplash.this);
        }

        }catch (Exception e){
            e.getMessage().toString();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ActivityUtil.REQUEST_CODE_PERMISSION: {
                boolean flagDenied = false;
                for(int x : grantResults){
                    if(x == -1){
                        flagDenied = true;
                        break;
                    }
                }
                if(flagDenied){
                    if(grantResults != null && grantResults.length>0){
                        for(int i=0; i< grantResults.length; i++){
                            if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                                // user rejected the permission - PERMISSION_DENIED is '-1' | ACCEPT is 0
                                boolean checkNaoPetube = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i].toString());//devolve false quando selecionado
                                if(!checkNaoPetube){
                                    activityUtil.showDialogPermissionsSystem(ViewSplash.this);
                                }else {
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i].toString())) {
                                        activityUtil.showDialogPermissions(ViewSplash.this, permissions[i].toString());
                                    }
                                }
                            }
                        }
                    }
                }else {

                    daoModelPresenter = new DaoModelPresenter(this, this);
                        /*
                Cria banco na memória interna e exporta esse mesmo banco para a memoria externa
             */
                    daoModelPresenter.createdbInterno();
                    //daoModelPresenter.createdbExterno();
                    //activityUtil.deleteDatabase(getApplicationContext());

                    startActivity(new Intent(this, ViewLogin.class));
                }

                return;
            }
        }
    }


    @Override
    public void sucess(String dsPackage) {
        Toast.makeText(this, dsPackage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSucessInternoDB() {
        try{
            //Toast.makeText(this, getString(R.string.sucessDbInterno), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ViewLogin.class));
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    public void showErrorInternoDB(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSucessExternoDB() {
        //Toast.makeText(this, R.string.sucessDbExterno, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorExternoDB(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }
}