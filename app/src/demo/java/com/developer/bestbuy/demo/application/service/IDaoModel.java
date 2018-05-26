package com.developer.bestbuy.demo.application.service;

public interface IDaoModel {
    void sucess(String dsPackage);
    void error(int resId);

    void showSucessInternoDB();
    void showErrorInternoDB(int resId);

    void showSucessExternoDB();
    void showErrorExternoDB(int resId);
}
