package com.example.panorama.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.panorama.Mediator;
import com.example.panorama.model.IModel;
import com.example.panorama.model.Model;
import com.example.panorama.view.TagsActivity;

import java.util.ArrayList;


public class Presenter implements IPresenter {

    private Mediator appMediador;
    private TagsActivity tagsActivity;
    private IModel modelo;


    private BroadcastReceiver receptorDeAvisos = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Mediator.AVISO_NUEVA_INFORMACION)){
                ArrayList<String> data = intent.getStringArrayListExtra(Mediator.CLAVE_INFORMACION);
                if (data != null){
                    tagsActivity.setWeatherTags(data);
                }
            }
        }
    };

    @Override
    public void getWeatherInfo() {
        appMediador.registerReceiver(receptorDeAvisos, Mediator.AVISO_NUEVA_INFORMACION);
    }



    public Presenter() {
        appMediador = Mediator.getInstance();
        tagsActivity = (TagsActivity) appMediador.getTagsActivity();
        modelo = Model.getInstance();
    }



}
