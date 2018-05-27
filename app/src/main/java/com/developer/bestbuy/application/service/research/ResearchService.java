package com.developer.bestbuy.application.service.research;

import android.content.Context;

import com.developer.bestbuy.application.service.IResearchView;
import com.developer.bestbuy.domain.model.Carro;
import com.developer.bestbuy.infrastructure.backend.ApiClient;
import com.developer.bestbuy.infrastructure.backend.ApiInterface;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ResearchService {
    private Context context;
    private IResearchView view;
    ApiInterface apiService;
    public List<Carro> resut;
    public Carro resutCarrId;

    public ResearchService(Context context, IResearchView view){
        this.context = context;
        this.view = view;
    }

    public List<Carro> buscar() throws IOException {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Carro>> call = apiService.getAllCars();
        call.enqueue(new Callback<List<Carro>>() {
            @Override
            public void onResponse(Response<List<Carro>> response, Retrofit retrofit) {
                if(response !=null){
                    resut = response.body();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                if (t != null){

                }
            }
        });
        return resut;
    }

    public Carro buscarId() throws IOException {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Carro> call = apiService.getCarById(view.getIdCarro());
        call.enqueue(new Callback<Carro>() {
            @Override
            public void onResponse(Response<Carro> response, Retrofit retrofit) {
                if(response !=null){
                    resutCarrId = response.body();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                if (t != null){

                }
            }
        });
        return resutCarrId;
    }
}
