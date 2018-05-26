package com.developer.bestbuy.domain.dao;

import android.content.ContentValues;

import com.developer.bestbuy.domain.DAO;
import com.developer.bestbuy.domain.model.Carro;
import com.developer.bestbuy.domain.model.Transacao;

import org.json.JSONObject;

import java.util.ArrayList;

public class CarroDao extends Carro implements DAO<Carro> {
    @Override
    public boolean save() throws Exception {
        return false;
    }

    @Override
    public boolean save(ContentValues values) throws Exception {
        return false;
    }

    @Override
    public boolean remove() throws Exception {
        return false;
    }

    @Override
    public boolean check(JSONObject object) throws Exception {
        return false;
    }

    @Override
    public Carro retrieve(long var1) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Carro> retrieveAll() throws Exception {
        return null;
    }

    @Override
    public ArrayList<Carro> retrieveSome(String var1, String var2) throws Exception {
        return null;
    }

    @Override
    public Transacao transmitir() throws Exception {
        return null;
    }
}
