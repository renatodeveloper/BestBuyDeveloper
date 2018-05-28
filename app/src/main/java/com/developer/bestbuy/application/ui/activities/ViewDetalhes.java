package com.developer.bestbuy.application.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.bestbuy.R;
import com.developer.bestbuy.application.service.IItensPedidoView;
import com.developer.bestbuy.application.service.IPedidoView;
import com.developer.bestbuy.application.service.IResearchView;
import com.developer.bestbuy.application.service.carrinho.ItensPedidoPresenter;
import com.developer.bestbuy.application.service.research.ResearchPresenter;
import com.developer.bestbuy.application.ui.adapter.CarroAdapter;
import com.developer.bestbuy.domain.model.Carro;
import com.developer.bestbuy.domain.model.ItensPedido;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_SHORT;

public class ViewDetalhes extends Activity implements IResearchView, IPedidoView, IItensPedidoView {
    private ResearchPresenter presenter;
    private ItensPedidoPresenter presenterItens;
    public Carro c;

    @BindView(R.id.title_text) TextView marca;
    @BindView(R.id.subtitle_text) TextView nome;
    @BindView(R.id.spinnerQtde) Spinner spinnerQtde;
    @BindView(R.id.media_image) ImageView imageView;
    @BindView(R.id.avatar_image) ImageView imageViewAvatar;
    @BindView(R.id.textViewDescricao) TextView descricao;
    @BindView(R.id.imageViewAdd) ImageView adicionar;
    @BindColor(R.color.color_red) int red;

    List<String> plantsList = null;
    ArrayAdapter<String> spinnerArrayAdapter = null;
    String[] value = null;

    public static int qtdeBuy = 2;

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
            c = gson.fromJson(bundle.getString(getApplicationContext().getResources().getString(R.string.key_detalhe)), Carro.class);

            presenter = new ResearchPresenter(this, this);
            presenterItens = new ItensPedidoPresenter(this, this, this);
            /*
            Get by idCarro
            if(c != null){
                presenter.buscarId();
            }
            */

            if(c != null){
                try{
                    marca.setText(c.getMarca());
                    nome.setText(c.getNome());
                    descricao.setText(c.getDescricao());

                    value = populaSpinner(c.getQuantidade());
                    plantsList = new ArrayList<>(Arrays.asList(value));

                    /*

                    spinnerArrayAdapter = new ArrayAdapter<String>(
                            this,R.layout.spinner_item,plantsList){
                        @Override
                        public boolean isEnabled(int position){
                            if(position == 0) {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            }
                            else{
                                return true;
                            }
                        }
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if(position == 0){
                                tv.setTextColor(Color.GRAY);
                            }
                            else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }

                    };
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    spinnerQtde.setAdapter(spinnerArrayAdapter);


                    spinnerQtde.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItemText = (String) parent.getItemAtPosition(position);
                            if(position > 0){
                               // qtdeBuy =0;
                               // qtdeBuy = position;
                                //Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                     */

                    Picasso.get().load(c.getImagem()).into(imageView);
                    Picasso.get().load(c.getImagem()).into(imageViewAvatar);
                }catch (Exception e){
                    e.getMessage().toString();
                }
            }
       }

    }

     @OnClick(R.id.imageViewAdd)
    public void submit() {
        try{
            presenterItens.registerNewItem();
            /*
            if(qtdeBuy>0){
                ItensPedido item = new ItensPedido();
                item.setIdCarro(c.getId());
                item.setPrecoUnitario(c.getPreco());
                item.setPrecoTotal(c.getPreco() * qtdeBuy);
                item.carro = c;
                item.setQuantidade(qtdeBuy);
                Home.carrinho.adiciona(item);
                startActivity(new Intent(this, Home.class));
            }else{
                Toast.makeText(getApplicationContext(), "Selecione uma QTDE ..." , Toast.LENGTH_SHORT).show();
            }
             */
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public static String[]populaSpinner(int qtde){
        String[] spinner = null;
        try{
            if(qtde>0){
                int cont = qtde+1;
                spinner = new String[cont];
                for(int s=0; s<qtde; s++){
                    if(s==0){
                        spinner[s]= "Selecione a qtde ...";
                    }else {
                        int idx = Integer.valueOf(s);
                        String lbl = String.valueOf(idx);
                        spinner[s] = lbl;
                    }
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return spinner;
    }

    @Override
    public void showResult(List<Carro> value) {

    }

    @Override
    public void showResult(Carro value) {
        try{

        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    public void error(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public Integer getIdCarro() {
        return c.getId();
    }

    @Override
    public Float getPrecoUnitario() {
        return c.getPreco();
    }

    @Override
    public int getQtde() {
        return qtdeBuy;
    }

    @Override
    public Float getTotalItemPedido() {
        return c.getPreco() * qtdeBuy;
    }

    @Override
    public String getValueJson() {
        String json= "";
        try{
            Gson gson = new Gson();
            json = gson.toJson(c);
        }catch (Exception e){
            e.getMessage().toString();
        }
        return json;
    }

    @Override
    public Carro getCarro() {
        return c;
    }

    @Override
    public void showErrorItensPedido(int resId) {

    }

    @Override
    public void resultOkIntensPedido() {
        startActivity(new Intent(this, Home.class));
    }

    @Override
    public int getIdPedido() {
        return ViewLogin.idPedidoSimulado;
    }

    @Override
    public int getIdCarroItemPedido() {
        return c.getId();
    }

    @Override
    public long getDataHora() {
        return System.currentTimeMillis();
    }

    @Override
    public String getDsDataHora() {
        DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
        return  df.format(this.getDataHora());
    }

    @Override
    public Double getTotal() {
        return null;
    }

    @Override
    public int getIdUsuario() {
        return ViewLogin.idUsuarioSimulado;
    }

    @Override
    public void showErrorPedido(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void resultOkPedido() {

    }
}
