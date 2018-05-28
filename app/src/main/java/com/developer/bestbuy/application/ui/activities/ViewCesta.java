package com.developer.bestbuy.application.ui.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.developer.bestbuy.application.service.IResearchView;
import com.developer.bestbuy.application.service.research.ResearchPresenter;
import com.developer.bestbuy.application.ui.adapter.AlbumsAdapter;
import com.developer.bestbuy.R;
import com.developer.bestbuy.application.ui.adapter.CarroAdapter;
import com.developer.bestbuy.application.ui.adapter.CestaAdapter;
import com.developer.bestbuy.domain.dao.CarroDao;
import com.developer.bestbuy.domain.model.Carro;
import com.developer.bestbuy.domain.model.ItensPedido;
import com.developer.bestbuy.domain.model.Pedido;
import com.developer.bestbuy.infrastructure.helper.ActivityUtil;
import com.developer.bestbuy.infrastructure.helper.DialogHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ViewCesta extends AppCompatActivity implements IResearchView {

    private RecyclerView recyclerView;
    private CestaAdapter adapter;
    private List<Carro> albumList;
    private ActivityUtil util;
    private DialogHelper helper;

    private ResearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);

        util = new ActivityUtil(getApplication());
        String dsTitle = util.getInfCesta(getApplicationContext());
        setTitle(dsTitle);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new CestaAdapter(this, albumList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String strCarro = bundle.getString(getApplicationContext().getResources().getString(R.string.key_detalhe));
            if (strCarro != null && strCarro.length() > 0) {
                try {
                    util.addItem(getApplicationContext(), strCarro);
                } catch (Exception e) {
                    e.getMessage().toString();
                }
            }

            try {
                List<Carro> value = util.recuperaListCarrosCarrinho(getApplicationContext());
                if (value.size() > 0) {
                    adapter = new CestaAdapter(this, value);
                    recyclerView.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.getMessage().toString();
            }


            prepareAlbums();

            try {
                util = new ActivityUtil(ViewCesta.this);
                helper = new DialogHelper(ViewCesta.this);

                Glide.with(this).load(R.drawable.car_home).into((ImageView) findViewById(R.id.backdrop));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {

                List<Carro> value = util.recuperaListCarrosCarrinho(getApplicationContext());
                adapter = new CestaAdapter(this, value);
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.car1,
                R.drawable.car2,
                R.drawable.car3,
                R.drawable.car4,
                R.drawable.car5,
                R.drawable.car6,
                R.drawable.car7,
                R.drawable.car8,
                R.drawable.car9,
                R.drawable.car10,
                R.drawable.car11};

        Carro a = new Carro("Caro A", 13, covers[0]);
        albumList.add(a);

        a = new Carro("Caro B", 8, covers[1]);
        albumList.add(a);

        a = new Carro("Caro C", 11, covers[2]);
        albumList.add(a);

        a = new Carro("Caro D", 12, covers[3]);
        albumList.add(a);

        a = new Carro("Caro E", 14, covers[4]);
        albumList.add(a);

        a = new Carro("Caro F", 1, covers[5]);
        albumList.add(a);

        a = new Carro("Caro G", 11, covers[6]);
        albumList.add(a);

        a = new Carro("Caro H", 14, covers[7]);
        albumList.add(a);

        a = new Carro("Caro I", 11, covers[8]);
        albumList.add(a);

        a = new Carro("Caro J", 17, covers[9]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    //fim

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_cesta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_finalizar) {
                util.limpaPrefJSON_CAR(getApplicationContext());
                util.limpaPref_if_Pedido(getApplicationContext());
                //util.limpaPref_if_Usuario(getApplicationContext());
            startActivity(new Intent(this, ViewLogin.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showResult(List<Carro> value) {
        try{
            adapter = new CestaAdapter(this, value);
            recyclerView.setAdapter(adapter);
            for(Carro c : value){
                if(c!=null){
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    public void showResult(Carro value) {

    }

    @Override
    public void error(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public Integer getIdCarro() {
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, Home.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
