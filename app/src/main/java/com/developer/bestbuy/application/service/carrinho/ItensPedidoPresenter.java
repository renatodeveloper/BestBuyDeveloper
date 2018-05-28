package com.developer.bestbuy.application.service.carrinho;

import android.content.Context;

import com.developer.bestbuy.R;
import com.developer.bestbuy.application.service.IItensPedidoView;
import com.developer.bestbuy.application.service.IPedidoView;
import com.developer.bestbuy.domain.model.Carro;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ItensPedidoPresenter {
    Context context;
    private IPedidoView      viewPedido;
    private IItensPedidoView viewItens;
    private ItensPedidoService service;

    public ItensPedidoPresenter(Context context, IPedidoView viewPedido, IItensPedidoView viewItens){
        this.context = context;
        this.service = new ItensPedidoService(context, viewPedido, viewItens);
        this.viewPedido = viewPedido;
        this.viewItens = viewItens;
    }


    public void registerNewItem(){
        try{
            if(viewPedido.getIdUsuario()<=0){
                viewPedido.showErrorPedido(R.string.itemPedidoGetIdUsuarioError);
                return;
            }
            //Pedido...
            if(viewPedido.getIdPedido()<=0){
                viewPedido.showErrorPedido(R.string.itemPedidoGetIdPedidoError);
                return;
            }
            if(viewPedido.getDataHora()<0){
                viewPedido.showErrorPedido(R.string.itemPedidoGetDataHoraError);
                return;
            }
            if(viewPedido.getDsDataHora().isEmpty()){
                viewPedido.showErrorPedido(R.string.itemPedidoGetDsDataHoraError);
                return;
            }
            //ItemPedido...

            if(viewItens.getValueJson().isEmpty()){
                viewPedido.showErrorPedido(R.string.itemGetPojoToGsonError);
                return;
            }
            if(viewItens.getIdPedido()<=0){
                viewPedido.showErrorPedido(R.string.itemPedidoGetIdPedidoError);
                return;
            }
            if(viewItens.getIdCarroItemPedido()<=0){
                viewPedido.showErrorPedido(R.string.itemPedidoGetIdPedidoError);
                return;
            }

            if(viewItens.getPrecoUnitario()<=0){
                viewPedido.showErrorPedido(R.string.itemPedidoGetIdPedidoError);
                return;
            }
            if(viewItens.getQtde()<=0){
                viewPedido.showErrorPedido(R.string.itemPedidoGetIdPedidoError);
                return;
            }
            if(viewItens.getTotalItemPedido()<=0){
                viewPedido.showErrorPedido(R.string.itemPedidoGetIdPedidoError);
                return;
            }

            boolean registerSucceeded = service.addItem();
            if(registerSucceeded){
                viewItens.resultOkIntensPedido();
            }else{
                viewItens.showErrorItensPedido(R.string.showErrorItemPedido);
            }

        }catch (Exception e){
            e.getMessage().toString();
        }
    }

}
