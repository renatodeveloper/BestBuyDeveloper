package com.developer.bestbuy.application.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.bestbuy.R;
import com.developer.bestbuy.domain.model.Carro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewDetalhes extends Activity {
    @BindView(R.id.title_text) TextView marca;
    @BindView(R.id.subtitle_text) TextView nome;
    @BindView(R.id.textViewQtde) TextView qtde;
    @BindView(R.id.media_image) ImageView imageView;
    @BindView(R.id.avatar_image) ImageView imageViewAvatar;
    @BindView(R.id.textViewDescricao) TextView descricao;
    @BindView(R.id.buttonAdicionar) Button adicionar;
    @BindColor(R.color.color_red) int red;
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_detalhes);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Gson gson = new GsonBuilder().create();
            Carro carro = gson.fromJson(bundle.getString(getApplicationContext().getResources().getString(R.string.key_detalhe)), Carro.class);
            if(carro != null){
                marca.setText(carro.getMarca());
                nome.setText(carro.getNome());
                descricao.setText(carro.getDescricao());
                qtde.setText("UNIDADES: " + String.valueOf(carro.getQuantidade()));
                qtde.setTextColor(red);

                Picasso.get().load(carro.getImagem()).into(imageView);
                Picasso.get().load(carro.getImagem()).into(imageViewAvatar);
            }
        }

    }

    @OnClick(R.id.buttonAdicionar)
    public void submit() {
        try{
            Toast.makeText(getApplicationContext(), "Adicionado", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
