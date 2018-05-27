package com.developer.bestbuy.infrastructure.backend;

import com.developer.bestbuy.domain.model.Carro;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

public interface ApiInterface {
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("v1/carro")
    Call<List<Carro>> getAllCars();
}
