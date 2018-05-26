package com.developer.bestbuy.demo.application.service;

public interface ILoginView {
    String getUsername();
    String getPassword();

    void showUsernameError(int resId);
    void showPasswordError(int resId);
    void showLoginError(int resId);
    void startMainActivity();
}
