package com.developer.bestbuy.application.service;

import com.developer.bestbuy.domain.model.Carro;

import java.util.List;

public interface IResearchView {
    void showResult(List<Carro> value);
    void error(int resId);
}
