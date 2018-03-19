package com.example.panorama.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.panorama.Mediator;
import com.example.panorama.model.IModel;
import com.example.panorama.model.Model;
import com.example.panorama.view.MainActivity;



public class Presenter implements IPresenter {

    private Mediator appMediador;
    private MainActivity vistaMapa;
    private IModel modelo;


    private BroadcastReceiver receptorDeGPS = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(Mediator.AVISO_LOCALIZACION_GPS)){
                Object[] datos = new Object[3];
                datos[0] = intent.getDoubleExtra(Mediator.CLAVE_LATITUD, 0);
                datos[1] = intent.getDoubleExtra(Mediator.CLAVE_LONGITUD, 0);
                datos[2] = "Mi ubicación";
            }
            Mediator.getInstance().unRegisterReceiver(this);
            Mediator.getInstance().registerReceiver(receptorDeGPS, Mediator.AVISO_LOCALIZACION_GPS);
        }
    };

    private BroadcastReceiver receptorDeAvisos = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(Mediator.AVISO_ESTADO_INICIAL)){

            }else if(intent.getAction().equals(Mediator.AVISO_AGREGAR_MARCA)){
                Object[] datos = new Object[3];
                datos[0] = intent.getDoubleExtra(Mediator.CLAVE_LATITUD, 0);
                datos[1] = intent.getDoubleExtra(Mediator.CLAVE_LONGITUD, 0);
                datos[2] = intent.getStringExtra(Mediator.CLAVE_TITULO);
            }
            Mediator.getInstance().unRegisterReceiver(this);
        }
    };



    public Presenter() {
        appMediador = Mediator.getInstance();
        vistaMapa = (MainActivity) appMediador.getMainView();
        modelo = Model.getInstance();
    }



}
