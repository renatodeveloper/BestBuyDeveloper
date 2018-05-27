package com.developer.bestbuy.application.service.research;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.developer.bestbuy.R;
import com.developer.bestbuy.application.service.IResearchView;
import com.developer.bestbuy.domain.model.Carro;

import java.util.List;

public class ResearchPresenter {
    Context context;
    private IResearchView view;
    private ResearchService service;

    public ResearchPresenter(Context context, IResearchView view){
        this.context = context;
        this.service = new ResearchService(context, view);
        this.view = view;
    }

    public void buscar() {
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.arg1 = 0;
                    msg.obj = null;
                    List<Carro> result = null;
                    do{
                        try{
                            result = service.buscar();
                            if(result != null && result.size()>0){
                                msg.arg1 = 1;
                                msg.obj = result;
                            }
                        }catch (Exception e){ e.getMessage().toString(); }
                    }while (result == null);
                    handler.sendMessage(msg);
                }
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if(msg.arg1 == 0){
                            view.error(R.string.research_error_busca);
                        }else{
                            List<Carro> result = (List<Carro>) msg.obj;

                            if(result != null && result.size()>0){
                                view.showResult(result);
                            }
                        }
                    }
                };
            }).start();
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
