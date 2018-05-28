package com.developer.bestbuy.domain.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.developer.bestbuy.R;
import com.developer.bestbuy.application.service.IDaoModel;
import com.developer.bestbuy.application.service.dao.DaoModelPresenter;
import com.developer.bestbuy.domain.DAO;
import com.developer.bestbuy.domain.model.Carro;
import com.developer.bestbuy.domain.model.Transacao;
import com.developer.bestbuy.infrastructure.helper.ActivityUtil;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CarroDao extends Carro implements DAO<Carro> {
    Context context;
    ActivityUtil util;

    private DaoModelPresenter daoModelPresenter;
    private IDaoModel viewIDaoModel;

    public long _id = 0;

    public long _idUser = 0;
    public long _idCar = 0;
    public long _idPedido = 0;
    public long _idItemPedido = 0;

    public static final String TABLE_NAME_CARRO= "Carro";
    public static final String TABLE_NAME_USUARIO= "Usuario";
    public static final String TABLE_NAME_PEDIDO= "Pedido";
    public static final String TABLE_NAME_ITEMPEDIDO= "ItemPedido";

    public CarroDao(Context context){
        this.daoModelPresenter = new DaoModelPresenter(context);
        this.context = context;
        this.util = new ActivityUtil();
    }
    @Override
    public boolean save() throws Exception {
        return false;
    }

    @Override
    public boolean save(ContentValues values) throws Exception {
        try{
            boolean result = false;

            SQLiteDatabase db = null;
            try {
                db = this.daoModelPresenter.getExternalDB();

                if (_id < 1) {
                    _id = db.insert(TABLE_NAME_ITEMPEDIDO, "", values);
                } else {
                    db.update(TABLE_NAME_ITEMPEDIDO, values, "idPedido=" + _id, null);
                }
                result = true;
            } catch (SQLiteException e){
                e.getMessage().toString();
                return result;
            }finally {
                db.close();
            }

            return result;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    @Override
    public boolean save(ContentValues valuesUser, ContentValues valuesPedido, ContentValues valuesItem) throws Exception {
        try{
            boolean result = false;

            SQLiteDatabase db = null;
            try {
                db = this.daoModelPresenter.getExternalDB();

                //*** CARRO: Caso não exista a tabela Carro a mesma é populada um unica vez
                if(!util.recuperaPref_if_Carro(context)){
                    JSONObject object;
                    ContentValues valuesCar;
                    String response = util.recuperaPrefJSON_CAR(context);
                    JSONArray jArray = new JSONArray(response);
                    for(int i=0; i<jArray.length();i++){
                        object = new JSONObject(jArray.get(i).toString());

                        if(object != null){
                            valuesCar = new ContentValues();
                            valuesCar.put(context.getString(R.string.idCarro), object.getString(context.getString(R.string.idCarro)));
                            valuesCar.put(context.getString(R.string.nomeCarro), object.getString(context.getString(R.string.nomeCarro)));
                            valuesCar.put(context.getString(R.string.dsCarro), object.getString(context.getString(R.string.dsCarro)));
                            valuesCar.put(context.getString(R.string.marcaCarro), object.getString(context.getString(R.string.marcaCarro)));
                            valuesCar.put(context.getString(R.string.quantidadeCarro), object.getString(context.getString(R.string.quantidadeCarro)));
                            valuesCar.put(context.getString(R.string.precoCarro), object.getString(context.getString(R.string.precoCarro)));
                            valuesCar.put(context.getString(R.string.imagemCarro), object.getString(context.getString(R.string.imagemCarro)));
                            db.insert(TABLE_NAME_CARRO, "", valuesCar);
                        }
                    }
                    util.definePref_if_Carro(context);
                }

                //*** USUÁRIO: Caso não exista a tabela Usuario o mesmo é incluido um unica vez - fluxo de teste
                if(!util.recuperaPref_if_Usuario(context)){
                    if (_idUser < 1) {
                        _idUser = db.insert(TABLE_NAME_USUARIO, "", valuesUser);
                    } else {
                        db.update(TABLE_NAME_USUARIO, valuesUser, "idUsuario=" + _idUser, null);
                    }
                    util.definePref_if_Usuario(context);
                }
                //*** PEDIDO: Caso não exista a instancia de pedido é adicionado o mesmo, esse preferences necessáriamente é limpado
                if(!util.recuperaPref_if_Pedido(context)){
                    if (_idPedido < 1) {
                        _idPedido = db.insert(TABLE_NAME_PEDIDO, "", valuesPedido);
                    } else {
                        db.update(TABLE_NAME_PEDIDO, valuesPedido, "idPedido=" + _idPedido, null);
                    }
                    util.definePref_if_Pedido(context);
                }

                //*** ITEMPEDIDO
                if (_idItemPedido < 1) {
                    _idItemPedido = db.insert(TABLE_NAME_ITEMPEDIDO, "", valuesItem);
                } else {
                    db.update(TABLE_NAME_ITEMPEDIDO, valuesItem, "idPedido=" + _idItemPedido, null);
                }

                result = true;
            } catch (SQLiteException e){
                e.getMessage().toString();
                return result;
            }finally {
                db.close();
            }

            return result;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    @Override
    public boolean remove() throws Exception {
        return false;
    }

    @Override
    public boolean check(JSONObject object) throws Exception {
        return false;
    }

    @Override
    public Carro retrieve(long var1) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Carro> retrieveAll() throws Exception {
        ArrayList<Carro> result = null;
        JSONObject object;
        Carro carro;
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

    @Override
    public ArrayList<Carro> retrieveSome(String var1, String var2) throws Exception {
        return null;
    }

    @Override
    public Transacao transmitir() throws Exception {
        return null;
    }
}
