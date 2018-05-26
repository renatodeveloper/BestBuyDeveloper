package com.developer.bestbuy.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Carro {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("descricao")
    @Expose
    private String descricao;
    @SerializedName("marca")
    @Expose
    private String marca;
    @SerializedName("quantidade")
    @Expose
    private Integer quantidade;
    @SerializedName("preco")
    @Expose
    private Float preco;
    @SerializedName("imagem")
    @Expose
    private String imagem;

    public Carro() {
    }

    public Carro(String name, int numOfSongs, int thumbnail) {
        this.nome = name;
        this.quantidade = numOfSongs;
        this.quantidade = thumbnail;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

}