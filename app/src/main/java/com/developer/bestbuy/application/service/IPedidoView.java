package com.developer.bestbuy.application.service;

public interface IPedidoView {
    int getIdPedido();
    long getDataHora();
    String getDsDataHora();
    Double getTotal();
    int getIdUsuario();

    void showLimitePedido(int resId);
    void showErrorPedido(int resId);
    void resultOkPedido();
}
