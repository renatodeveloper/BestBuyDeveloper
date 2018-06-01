package com.developer.bestbuy.application.service.carrinho;

import android.content.ContentValues;
import android.content.Context;

import com.developer.bestbuy.R;
import com.developer.bestbuy.application.service.IItensPedidoView;
import com.developer.bestbuy.application.service.IPedidoView;
import com.developer.bestbuy.domain.dao.CarroDao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;

public class ItensPedidoService {

    private Context context;
    private IPedidoView viewPedido;
    private IItensPedidoView viewItens;

    private CarroDao carroDao;

    public ItensPedidoService(Context context, IPedidoView viewPedido, IItensPedidoView viewItens){
        this.context = context;
        this.viewPedido = viewPedido;
        this.viewItens = viewItens;
    }

    public boolean  addItem() {
        try{
            carroDao = new CarroDao(this.context);


            ContentValues valuesUser = new ContentValues();
                            valuesUser.put(context.getString(R.string.idTblUser), viewPedido.getIdUsuario());
                            valuesUser.put(context.getString(R.string.dsNomeTblUser),  "USUÁRIO SIMULADO");
                            valuesUser.put(context.getString(R.string.dsLoginTblUser), "USUÁRIO SIMULADO");
                            valuesUser.put(context.getString(R.string.dsSenhaTblUser), "USUÁRIO SIMULADO");
                            valuesUser.put(context.getString(R.string.namePhoto),      "USUÁRIO SIMULADO");
                            //valuesUser.put(context.getString(R.string.bytePhoto), "TESTE");

            ContentValues valuesPedido = new ContentValues();
                            valuesPedido.put(context.getString(R.string.idPedidoPedido), viewPedido.getIdPedido());
                            valuesPedido.put(context.getString(R.string.dataHoraPedido), viewPedido.getDataHora());
                            valuesPedido.put(context.getString(R.string.dsDataHoraPedido), viewPedido.getDsDataHora());
                            valuesPedido.put(context.getString(R.string.totalPedido), viewPedido.getTotal());
                            valuesPedido.put(context.getString(R.string.idUsuarioPedido), viewPedido.getIdUsuario());

            ContentValues valuesItemPedido = new ContentValues();
                            valuesItemPedido.put(context.getString(R.string.idPedidoItemPedido), viewPedido.getIdPedido());
                            valuesItemPedido.put(context.getString(R.string.idCarroItemPedido), viewItens.getCarro().getId());
                            valuesItemPedido.put(context.getString(R.string.precoUnitarioItemPedido), viewItens.getPrecoUnitario());
                            valuesItemPedido.put(context.getString(R.string.quantidadeItemPedido), viewItens.getQtde());
                            valuesItemPedido.put(context.getString(R.string.precoTotalItemPedido), viewItens.getTotalItemPedido());
                            valuesItemPedido.put(context.getString(R.string.dsPojoToGson), viewItens.getValueJson());

            return carroDao.save(valuesUser, valuesPedido, valuesItemPedido);

        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    public boolean checkLimite(){
        carroDao = new CarroDao(this.context);
        ContentValues valuesPedido = new ContentValues();
        valuesPedido.put(context.getString(R.string.idPedidoPedido), viewPedido.getIdPedido());
        valuesPedido.put(context.getString(R.string.dataHoraPedido), viewPedido.getDataHora());
        valuesPedido.put(context.getString(R.string.dsDataHoraPedido), viewPedido.getDsDataHora());
        valuesPedido.put(context.getString(R.string.totalPedido), viewPedido.getTotal());
        valuesPedido.put(context.getString(R.string.idUsuarioPedido), viewPedido.getIdUsuario());

        ContentValues valuesItemPedido = new ContentValues();
        valuesItemPedido.put(context.getString(R.string.idPedidoItemPedido), viewPedido.getIdPedido());
        valuesItemPedido.put(context.getString(R.string.idCarroItemPedido), viewItens.getCarro().getId());
        valuesItemPedido.put(context.getString(R.string.precoUnitarioItemPedido), viewItens.getPrecoUnitario());
        valuesItemPedido.put(context.getString(R.string.quantidadeItemPedido), viewItens.getQtde());
        valuesItemPedido.put(context.getString(R.string.precoTotalItemPedido), viewItens.getTotalItemPedido());
        valuesItemPedido.put(context.getString(R.string.dsPojoToGson), viewItens.getValueJson());

        if(carroDao.checkLimiteCompra(valuesPedido, valuesItemPedido)){
            return true;
        }else{
            return  false;
        }
    }

}
