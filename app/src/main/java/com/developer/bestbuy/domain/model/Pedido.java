package com.developer.bestbuy.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Integer idPedido;
    private long dataHora;
    private String dsDataHora;
    private Double total;
    private Integer idUsuario;

    private List<ItensPedido> itens = new ArrayList<ItensPedido>();

    public void adiciona(ItensPedido item) {
        itens.add(item);
        //total += item.getProduto().getPreco() * item.getQuantidade();
    }


    public int getSizePedido(){
        return  itens.size();
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public long getDataHora() {
        return dataHora;
    }

    public void setDataHora(long dataHora) {
        this.dataHora = dataHora;
    }

    public String getDsDataHora() {
        return dsDataHora;
    }

    public void setDsDataHora(String dsDataHora) {
        this.dsDataHora = dsDataHora;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<ItensPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItensPedido> itens) {
        this.itens = itens;
    }
}