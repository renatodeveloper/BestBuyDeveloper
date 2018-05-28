package com.developer.bestbuy.infrastructure.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.developer.bestbuy.R;
import com.developer.bestbuy.application.service.dao.DaoModelPresenter;
import com.developer.bestbuy.application.ui.activities.ViewCesta;
import com.developer.bestbuy.application.ui.activities.ViewLogin;
import com.developer.bestbuy.domain.model.Carro;
import com.developer.bestbuy.domain.model.ItensPedido;
import com.developer.bestbuy.domain.model.Pedido;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    public ActivityUtil(Context ctx){
        this.context = ctx;
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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //********************************************** start shared endPoint getAllCars
    public static  void definePrefJSON_CAR(Context context, JSONArray array){
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonCar), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(context.getString(R.string.valuePrefJsonCar), array.toString());
            editor.commit();
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public String recuperaPrefJSON_CAR(Context context){
        String result = new String();
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonCar), Context.MODE_PRIVATE);
            result = mPrefs.getString(context.getString(R.string.valuePrefJsonCar), "");
        }catch (Exception e){
            e.getMessage().toString();
        }
        return result;
    }

    public static  void limpaPrefJSON_CAR(Context context){
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonCar), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();

            editor.commit();
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
    //********************************************** Flag existe TBL Carro
    public void definePref_if_Carro(Context context) {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonCar), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(context.getString(R.string.flagPrefCarro), true);
            editor.commit();
        }catch (Exception e) {
            e.getMessage().toString();
        }
    }
    public boolean recuperaPref_if_Carro(Context context){
        JSONObject result = new JSONObject();
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonCar), Context.MODE_PRIVATE);
            if(mPrefs.getBoolean(context.getString(R.string.flagPrefCarro), false)){
                return true;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }
    public void limpaPref_if_Carro(Context context) {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonCar), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();

            editor.commit();

        }catch (Exception e) {
            e.getMessage().toString();
        }
    }

    //********************************************** Flag existe TBL Usuáio
    public void definePref_if_Usuario(Context context) {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonUser), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(context.getString(R.string.flagPrefUsuario), true);
            editor.commit();
        }catch (Exception e) {
            e.getMessage().toString();
        }
    }
    public boolean recuperaPref_if_Usuario(Context context){
        JSONObject result = new JSONObject();
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonUser), Context.MODE_PRIVATE);
            if(mPrefs.getBoolean(context.getString(R.string.flagPrefUsuario), false)){
                return true;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }
    public void limpaPref_if_Usuario(Context context) {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonUser), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();

            editor.commit();

        }catch (Exception e) {
            e.getMessage().toString();
        }
    }

    //********************************************** Flag existe TBL Pedido, essa tem que limpar após logout
    public void definePref_if_Pedido(Context context) {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonPed), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(context.getString(R.string.flagPrefPedido), true);
            editor.commit();
        }catch (Exception e) {
            e.getMessage().toString();
        }
    }
    public boolean recuperaPref_if_Pedido(Context context){
        JSONObject result = new JSONObject();
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonPed), Context.MODE_PRIVATE);
            if(mPrefs.getBoolean(context.getString(R.string.flagPrefPedido), false)){
                return true;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }
    public void limpaPref_if_Pedido(Context context) {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.filePrefJsonPed), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();

            editor.commit();

        }catch (Exception e) {
            e.getMessage().toString();
        }
    }

    public List<Carro> recuperaListCarros(Context context) throws Exception {
        List<Carro> result = new ArrayList<>();
        JSONObject object;
        Carro carro;
        ActivityUtil util = new ActivityUtil();
        try{
            String response = util.recuperaPrefJSON_CAR(context);
            JSONArray jArray = new JSONArray(response);
            for(int i=0; i<jArray.length();i++){
                object = new JSONObject(jArray.get(i).toString());
                carro = new Carro();
                if(object != null){
                    carro.setId(Integer.valueOf(object.getString(context.getString(R.string.idCarro))));
                    carro.setNome(object.getString(context.getString(R.string.nomeCarro)));
                    carro.setDescricao(object.getString(context.getString(R.string.dsCarro)));
                    carro.setMarca(object.getString(context.getString(R.string.marcaCarro)));
                    carro.setQuantidade(Integer.valueOf(object.getString(context.getString(R.string.quantidadeCarro))));
                    carro.setPreco(Float.valueOf(object.getString(context.getString(R.string.precoCarro))));
                    carro.setImagem(object.getString(context.getString(R.string.imagemCarro)));

                    result.add(carro);
                }
            }

        }catch (Exception e){
            e.getMessage().toString();
        }
        return result;
    }

    public List<Carro> recuperaListCarrosCarrinho(Context context) throws Exception {
        Carro carro;
        ItensPedido itensPedido;
        Integer  idCarro;
        List<Carro> result = new ArrayList<>();

        DaoModelPresenter daoModelPresenter;
        SQLiteDatabase db = null;
        try {
            daoModelPresenter = new DaoModelPresenter(context);
            db = daoModelPresenter.getExternalDB();
            Cursor cursor;
            if(db!= null){
                String[] argsCARRO;
                String[] args = {String.valueOf(ViewLogin.idPedidoSimulado)};//SIMULAÇÃO
                cursor = db.query(this.context.getString(R.string.tblNameItensPedido), null, "idPedido=?", args, null,null,null);
                int qtde = cursor.getCount();
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    argsCARRO = new String[qtde];
                    for(int x=0;x<cursor.getCount();x++){
                        itensPedido = new ItensPedido();
                        Integer idPedido = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idPedidoItemPedido)));
                        idCarro = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idCarroItemPedido)));
                        Float   precoUnitario = cursor.getFloat(cursor.getColumnIndex(context.getString(R.string.precoUnitarioItemPedido)));
                        Integer  quantidade = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.quantidadeItemPedido)));
                        Float    precoTotal = cursor.getFloat(cursor.getColumnIndex(context.getString(R.string.precoTotalItemPedido)));

                        itensPedido.setIdPedido(idPedido);
                        itensPedido.setIdCarro(idCarro);
                        itensPedido.setPrecoUnitario(precoUnitario);
                        itensPedido.setQuantidade(quantidade);
                        itensPedido.setPrecoTotal(precoTotal);

                        argsCARRO [x]= String.valueOf(idCarro);
                        cursor.moveToNext();
                    }

                    if(argsCARRO != null && argsCARRO.length>0){
                        for(int y=0;y<argsCARRO.length;y++){
                            String[] argsExtras = {argsCARRO[y]};
                            cursor = db.query(this.context.getString(R.string.tblNameCarro), null, "id=?", argsExtras, null,null,null);

                            int qtdeCARRO = cursor.getCount();
                            if(cursor.getCount()>0) {
                                cursor.moveToFirst();
                                do {
                                    carro = new Carro();

                                    Integer id = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idCarro)));
                                    String nome  = cursor.getString(cursor.getColumnIndex(context.getString(R.string.nomeCarro)));
                                    String descricao  = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsCarro)));
                                    String marca  = cursor.getString(cursor.getColumnIndex(context.getString(R.string.marcaCarro)));
                                    Integer quantidade = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.quantidadeCarro)));
                                    Float preco = cursor.getFloat(cursor.getColumnIndex(context.getString(R.string.precoCarro)));
                                    String imagem = cursor.getString(cursor.getColumnIndex(context.getString(R.string.imagemCarro)));


                                    carro.setId(id);
                                    carro.setNome(nome);
                                    carro.setDescricao(descricao);
                                    carro.setMarca(marca);
                                    carro.setQuantidade(quantidade);
                                    carro.setPreco(preco);
                                    carro.setImagem(imagem);

                                    result.add(carro);
                                } while (cursor.moveToNext());
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }

        return result;
    }



    public boolean addItem(Context context, String str){
        Cursor cursor;
        boolean result;
        ItensPedido itensPedido;
        try{
            Gson gson = new GsonBuilder().create();
            Carro  c = gson.fromJson(str, Carro.class);
            ContentValues valuesItenPedido;
            DaoModelPresenter daoModelPresenter;
            SQLiteDatabase db = null;
            try {
                daoModelPresenter = new DaoModelPresenter(context);
                db = daoModelPresenter.getExternalDB();
                if (db != null) {

                    String[] args = {String.valueOf(ViewLogin.idPedidoSimulado)};//SIMULAÇÃO
                    cursor = db.query(this.context.getString(R.string.tblNameItensPedido), null, "idPedido=?", args, null,null,null);
                    if(cursor.getCount()>0) {
                        cursor.moveToFirst();
                        itensPedido = new ItensPedido();
                        Integer idPedido = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idPedidoItemPedido)));
                        Float   precoUnitario = cursor.getFloat(cursor.getColumnIndex(context.getString(R.string.precoUnitarioItemPedido)));
                        Integer  quantidade = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.quantidadeItemPedido)));
                        Float    precoTotal = cursor.getFloat(cursor.getColumnIndex(context.getString(R.string.precoTotalItemPedido)));

                        itensPedido.setIdPedido(idPedido);
                        itensPedido.setIdCarro(c.getId());
                        itensPedido.setPrecoUnitario(precoUnitario);
                        itensPedido.setQuantidade(quantidade);
                        itensPedido.setPrecoTotal(precoTotal);

                        db = daoModelPresenter.getExternalDB();
                        if (db != null) {
                            valuesItenPedido = new ContentValues();
                            valuesItenPedido.put(context.getString(R.string.idPedidoItemPedido), ViewLogin.idPedidoSimulado);
                            valuesItenPedido.put(context.getString(R.string.idCarroItemPedido), c.getId());
                            valuesItenPedido.put(context.getString(R.string.precoUnitarioItemPedido), itensPedido.getPrecoUnitario());
                            valuesItenPedido.put(context.getString(R.string.quantidadeItemPedido), c.getQuantidade());
                            valuesItenPedido.put(context.getString(R.string.precoTotalItemPedido),itensPedido.getPrecoTotal());

                            db.insert(this.context.getString(R.string.tblNameItensPedido), "", valuesItenPedido);
                        }
                    }
                }
            }catch (Exception e){
                e.getMessage().toString();
            }

        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    public boolean removeItem(Context context, String str){
        Cursor cursor;
        boolean result;
        try{
            Gson gson = new GsonBuilder().create();
            Carro  c = gson.fromJson(str, Carro.class);

            DaoModelPresenter daoModelPresenter;
            SQLiteDatabase db = null;
            try {
                daoModelPresenter = new DaoModelPresenter(context);
                db = daoModelPresenter.getExternalDB();
                if (db != null) {
                    result = db.delete(this.context.getString(R.string.tblNameItensPedido), "idPedido" + "=" + ViewLogin.idPedidoSimulado, null) > 0;
                }
            }catch (Exception e){
                e.getMessage().toString();
            }

        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    public String getInfCesta(Context context){
        String result = "SEM PRODUTOS NA SACOLA";
        Cursor cursor;
        ItensPedido itensPedido;
        try{
            DaoModelPresenter daoModelPresenter;
            SQLiteDatabase db = null;
            try {
                daoModelPresenter = new DaoModelPresenter(context);
                db = daoModelPresenter.getExternalDB();
                if (db != null) {
                    String[] args = {String.valueOf(ViewLogin.idPedidoSimulado)};//SIMULAÇÃO
                    cursor = db.query(this.context.getString(R.string.tblNameItensPedido), null, "idPedido=?", args, null,null,null);
                    if(cursor.getCount()>0) {
                        if(cursor.getCount()>0) {
                            cursor.moveToFirst();
                            itensPedido = new ItensPedido();
                            Integer idPedido = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idPedidoItemPedido)));
                            Float precoUnitario = cursor.getFloat(cursor.getColumnIndex(context.getString(R.string.precoUnitarioItemPedido)));
                            Integer quantidade = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.quantidadeItemPedido)));
                            Float precoTotal = cursor.getFloat(cursor.getColumnIndex(context.getString(R.string.precoTotalItemPedido)));

                            itensPedido.setPrecoUnitario(precoUnitario);
                            itensPedido.setQuantidade(quantidade);
                            itensPedido.setPrecoTotal(precoTotal);
                            result =  "Total: " + String.valueOf((precoUnitario * quantidade));
                        }
                    }
                }
            }catch (Exception e){
                e.getMessage().toString();
            }
        }catch (Exception e) {
            e.getMessage().toString();
        }
        return result;
    }
}
