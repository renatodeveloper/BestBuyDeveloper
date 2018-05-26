package com.developer.bestbuy.full.application.service;

public interface IDaoModel {
    void sucess(String dsPackage);
    void error(int resId);

    void showSucessInternoDB();
    void showErrorInternoDB(int resId);

    void showSucessExternoDB();
    void showErrorExternoDB(int resId);
}
