package com.developer.bestbuy.application.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.developer.bestbuy.R;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewCesta extends Activity {
    @BindView(R.id.marca)
    TextView marca;
    @BindView(R.id.preco) TextView preco;
    @BindView(R.id.quantidade)
    TextView quantidade;
    @BindView(R.id.detalhes)
    Button detalhes;
    @BindView(R.id.remover)
    Button remover;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_cesta);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.detalhes)
    public void submit() {
        try{
            startActivity(new Intent(this, ViewDetalhes.class));
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @OnClick(R.id.remover)
    public void remover() {
        try{

        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
