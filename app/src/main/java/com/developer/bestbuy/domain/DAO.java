package com.developer.bestbuy.domain;


import android.content.ContentValues;

import com.developer.bestbuy.domain.model.Transacao;

import org.json.JSONObject;

import java.util.ArrayList;

public interface DAO<E> {
    boolean save() throws Exception;

    boolean save(ContentValues values) throws Exception;

    boolean save(ContentValues values1, ContentValues values2, ContentValues value3) throws Exception;

    boolean remove() throws Exception;

    boolean check(JSONObject object) throws Exception;

    E retrieve(long var1) throws Exception;

    ArrayList<E> retrieveAll() throws Exception;

    ArrayList<E> retrieveSome(String var1, String var2) throws Exception;

    Transacao transmitir() throws Exception;
}
