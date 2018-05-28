package com.developer.bestbuy.application.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.developer.bestbuy.R;
import com.developer.bestbuy.application.ui.activities.Home;
import com.developer.bestbuy.application.ui.activities.ViewCesta;
import com.developer.bestbuy.application.ui.activities.ViewDetalhes;
import com.developer.bestbuy.domain.model.Carro;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Carro> produtoList;
    Gson gson = null;
    String str = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, countt;
        public ImageView photoCar, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.marca);
            count = (TextView) view.findViewById(R.id.modelo);
            countt = (TextView) view.findViewById(R.id.preco);
            photoCar = (ImageView) view.findViewById(R.id.car_photo);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public CarroAdapter(Context mContext, List<Carro> produtoList) {
        this.mContext = mContext;
        this.produtoList = produtoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Carro carro = produtoList.get(position);
        holder.title.setText(carro.getMarca());
        holder.count.setText(carro.getNome());
        holder.countt.setText("R$: " + String.valueOf(carro.getPreco()));
        holder.countt.setTextColor(Color.RED);


        Picasso.get().load(carro.getImagem()).into(holder.photoCar);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "";
                gson = new Gson();
                gson.toJson(carro);
                str = gson.toJson(carro).toString();
                showPopupMenu(holder.overflow);
            }
        });

        /*


        // loading carro cover using Glide library
        Glide.with(mContext).load(carro.getImage()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
          */
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_car, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_detalhes:

                    Bundle b = new Bundle();
                    b.putString(mContext.getResources().getString(R.string.key_detalhe), str);
                    mContext.startActivity(new Intent(mContext, ViewDetalhes.class).putExtras(b));
                    return true;
                case R.id.action_add_comprar:
                    Bundle bb = new Bundle();
                    bb.putString(mContext.getResources().getString(R.string.key_detalhe), str);
                    mContext.startActivity(new Intent(mContext, ViewCesta.class).putExtras(bb));
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

}