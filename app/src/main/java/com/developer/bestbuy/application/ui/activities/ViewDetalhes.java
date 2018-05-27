package com.developer.bestbuy.application.ui.activities;

import android.app.Activity;
import android.content.Context;
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
import com.developer.bestbuy.application.service.IResearchView;
import com.developer.bestbuy.application.service.research.ResearchPresenter;
import com.developer.bestbuy.application.ui.adapter.CarroAdapter;
import com.developer.bestbuy.domain.model.Carro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewDetalhes extends Activity implements IResearchView {
    private ResearchPresenter presenter;
    public Carro c;

    @BindView(R.id.title_text) TextView marca;
    @BindView(R.id.subtitle_text) TextView nome;
    @BindView(R.id.spinnerQtde) Spinner spinnerQtde;
    @BindView(R.id.media_image) ImageView imageView;
    @BindView(R.id.avatar_image) ImageView imageViewAvatar;
    @BindView(R.id.textViewDescricao) TextView descricao;
    @BindView(R.id.imageViewAdd) ImageView adicionar;
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
            c = gson.fromJson(bundle.getString(getApplicationContext().getResources().getString(R.string.key_detalhe)), Carro.class);
            /*
            Get by idCarro
            if(c != null){
                presenter = new ResearchPresenter(this, this);
                presenter.buscarId();
            }
            */

            if(c != null){
                marca.setText(c.getMarca());
                nome.setText(c.getNome());
                descricao.setText(c.getDescricao());

                String[] value = populaSpinner(c.getQuantidade());

                final List<String> plantsList = new ArrayList<>(Arrays.asList(value));
                final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
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
                            Toast.makeText
                                    (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                /*
                ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, R.layout.spinner_item, value);
                ArrayAdapter<Integer> spinnerArrayAdapter = arrayAdapter;
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerQtde.setAdapter(spinnerArrayAdapter);
                */

                Picasso.get().load(c.getImagem()).into(imageView);
                Picasso.get().load(c.getImagem()).into(imageViewAvatar);
            }
       }

    }

     @OnClick(R.id.imageViewAdd)
    public void submit() {
        try{
            Home.carrinho.add(c);
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
}
