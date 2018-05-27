package com.developer.bestbuy.infrastructure.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.developer.bestbuy.R;

public class DialogHelper {
    private Context context;

    public DialogHelper(Context ctx){
        this.context = ctx;
    }

    public void noConection(final Context context){
        new AlertDialog.Builder(context)
                .setTitle(this.context.getResources().getString(R.string.title_msg_conection))
                .setMessage(this.context.getResources().getString(R.string.desc_msg_conection))
                .setCancelable(false)
                .setPositiveButton(this.context.getResources().getString(R.string.opt_msg_conection), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                    }
                }).show();
    }
}
