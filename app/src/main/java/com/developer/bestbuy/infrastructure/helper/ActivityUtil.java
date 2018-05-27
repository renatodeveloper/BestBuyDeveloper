package com.developer.bestbuy.infrastructure.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.developer.bestbuy.R;

import java.io.File;

public class ActivityUtil {
    Context context;
    //localização...
    public static final int REQUEST_CODE_PERMISSION = 1;
    public static String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    //localização...

    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
    };

    public ActivityUtil() {
    }

    /*
    Cria o folder caso não exista
     */
    public static boolean checkIfExistFolder(Context context){
        File f;
        String path = "";
        try{
            path = Environment.getExternalStorageDirectory().toString() + context.getResources().getString(R.string.folder);
            f = new File(path);
            if (!f.exists()) {
                if (!f.mkdirs()) {
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }


    public void verifyStoragePermissionsAll(Activity activity) {
        try{
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_CODE_PERMISSION
            );
        }catch (Exception e){
            e.getMessage().toString();
        }

    }

    public static void showDialogPermissionsSystem(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.str_atencao));
        builder.setMessage(context.getString(R.string.str_infoPermissaoNeverAgain));
        builder.setPositiveButton(context.getString(R.string.str_finalizar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                dialog.dismiss();
                System.exit(0);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showDialogPermissions(final Context context, String valeu){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.str_atencao));
        builder.setMessage(valeu + context.getString(R.string.str_infoPermissao));
        builder.setPositiveButton(context.getString(R.string.str_finalizar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                dialog.dismiss();
                System.exit(0);
            }
        });

        /* builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        }); */

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void deleteDatabase(Context context) {
        // DEMO: /data/user/0/com.developer.bestbuy.demo/databases/CARRO.db
        // FULL: /data/user/0/com.developer.bestbuy.full/databases/CARRO.db
        File data = Environment.getDataDirectory();
        String dbPath = "/data/com.developer.bestbuy.demo.debug/databases/CARRO.db";
        String dsPathExtra = "/user/0/com.possoajudar.app.full.debug/databases/CARRO.db";
        File current_db = new File(data, dbPath);
        File current_dbII = new File(data, dsPathExtra);
        try {
            if(current_db.exists()){
                current_db.delete();
            }
            if(current_dbII.exists()){
                current_dbII.delete();
            }
        } catch (Exception e) {
            e.getMessage().toString();
        }
    }
}
