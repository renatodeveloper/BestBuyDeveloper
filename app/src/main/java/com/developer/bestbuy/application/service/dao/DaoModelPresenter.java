package com.developer.bestbuy.application.service.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.developer.bestbuy.R;
import com.developer.bestbuy.application.service.IDaoModel;

public class DaoModelPresenter {
    private IDaoModel viewIDaoModel;
    private DaoModelService service;

    public DaoModelPresenter(Context contex){
        this.service = new DaoModelService(contex);
    }

    public DaoModelPresenter(Context context, IDaoModel view ){
        this.service = new DaoModelService(context);
        this.viewIDaoModel = view;
    }

    public SQLiteDatabase getInternalDB(){
        SQLiteDatabase database = null;
        try{
            database =  this.service.getdbInterno();
        }catch (Exception e){
            e.getMessage().toString();
        }
        return database;
    }


    public void closeInternalDB(SQLiteDatabase database){
        try{
            this.service.closeDb(database);
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public SQLiteDatabase getExternalDB(){
        SQLiteDatabase database = null;
        try{
            database =  this.service.getdbExterno();
        }catch (Exception e){
            e.getMessage().toString();
        }
        return database;
    }
    public void closeExternallDB(SQLiteDatabase database){
        try{
            this.service.closeDb(database);
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public void createdbInterno(){
        try{
            if( this.service.createdbInterno()){
                if( this.service.getdbInterno() != null){
                    if( this.service.exportInternoToExternoDB()){
                        //if( this.service.getdbExterno() != null){//cria db externo
                        this.viewIDaoModel.showSucessInternoDB();
                        //}else{
                        //this.viewIDaoModel.showErrorExternoDB(R.string.errorDbCreateMemoriaExterna);
                        //}
                    }else{
                        this.viewIDaoModel.showErrorInternoDB(R.string.errorDbCreateMemoriaInterna);
                    }
                }else{
                    this.viewIDaoModel.showErrorInternoDB(R.string.errorDbCreateMemoriaInterna);
                }
                this.viewIDaoModel.showSucessInternoDB();
            }else{
                this.viewIDaoModel.showErrorInternoDB(R.string.errorDbCreateMemoriaInterna);
            }

        }catch (Exception e){
            this.viewIDaoModel.showErrorInternoDB(R.string.errorDbCreateMemoriaInterna);
            e.getMessage().toString();
        }
    }

    public void createdbExterno(){
        try{
            if(this.service.createFolder()){
                if(this.service.createdbExterno()){
                    this.viewIDaoModel.showSucessExternoDB();
                }else{
                    this.viewIDaoModel.showErrorExternoDB(R.string.errorDbCreateMemoriaExterna);
                }
            }else{
                this.viewIDaoModel.showErrorExternoDB(R.string.errorCreateFolderExterna);
            }

        }catch (Exception e){
            this.viewIDaoModel.showErrorExternoDB(R.string.errorDbCreateMemoriaExterna);
            e.getMessage().toString();
        }
    }


    public void checkExistDbInterno(){
        SQLiteDatabase database;
        try{
            database = service.getdbExterno();
            if(database != null){
                database.execSQL("");
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }


}
