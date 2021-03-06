package com.developer.bestbuy.application.ui.adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Carro> listCar;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView photoCar, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.marca);
            count = (TextView) view.findViewById(R.id.modelo);
            photoCar = (ImageView) view.findViewById(R.id.car_photo);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public AlbumsAdapter(Context mContext, List<Carro> albumList) {
        this.mContext = mContext;
        this.listCar = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Carro carro = listCar.get(position);
        holder.title.setText(carro.getNome());
        holder.count.setText(carro.getQuantidade() + " songs");

        // loading car cover using Glide library
        Glide.with(mContext).load(carro.getQuantidade()).into(holder.photoCar);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
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
                    mContext.startActivity(new Intent(mContext, ViewDetalhes.class));
                    return true;
                case R.id.action_add_comprar:
                    mContext.startActivity(new Intent(mContext, ViewCesta.class));
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return listCar.size();
    }
}

