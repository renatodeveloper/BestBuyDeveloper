package com.developer.bestbuy.application.service;

import com.developer.bestbuy.domain.model.Carro;
import com.developer.bestbuy.domain.model.ItensPedido;

public interface IItensPedidoView {
    int getIdPedido();
    int getIdCarroItemPedido();
    Float getPrecoUnitario();
    int   getQtde();
    Float getTotalItemPedido();
    String getValueJson();
    Carro getCarro();

    void showErrorItensPedido(int resId);
    void resultOkIntensPedido();
}
