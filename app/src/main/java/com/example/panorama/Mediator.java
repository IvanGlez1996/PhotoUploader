package com.example.panorama;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.example.panorama.presenter.IPresenter;
import com.example.panorama.presenter.Presenter;
import com.example.panorama.view.IMainActivity;
import com.example.panorama.view.ITagsActivity;
import com.example.panorama.view.TagsActivity;

/**
 * Created by IvanGlez on 12/03/2018.
 */

public class Mediator extends Application {
    private static Mediator singleton;

    // variables correspondientes a los presentadores, vistas y modelo
    private IPresenter presenter;
    private IMainActivity mainView;
    private ITagsActivity tagsActivity;

    // constantes de comunicación, almacenamiento y petición
    public static final int ZOOM = 12; //este valor debería ser una preferencia de la aplicación, pero como no tenemos...
    public static final int ESTADO_INICIAL = 0;
    public static final int ESTADO_AGREGAR_MARCA = 1;
    public static final int ESTADO_BORRAR_MARCA = 2;
    public static final String CLAVE_LATITUD = "latitud";
    public static final String CLAVE_LONGITUD = "longitud";
    public static final String CLAVE_TITULO = "titulo";
    public static final String CLAVE_MARCAS = "marcas";

    public static final String AVISO_ESTADO_INICIAL = "pem.tema4.AVISO_ESTADO_INICIAL";
    public static final String AVISO_LOCALIZACION_GPS = "pem.tema4.AVISO_LOCALIZACION_GPS";
    public static final String AVISO_AGREGAR_MARCA = "pem.tema4.AVISO_AGREGAR_MARCA";
    public static final String AVISO_BORRAR_MARCA = "pem.tema4.AVISO_BORRAR_MARCA";


    public static Mediator getInstance(){
        return singleton;
    }

    // Métodos accessor de los presentadores, vistas y modelo
    public IPresenter getPresenter() {
        if (presenter == null)
            presenter = new Presenter();
        return presenter;
    }

    public void removePresentadorMapa() {
        presenter = null;
    }

    public IMainActivity getMainView() {
        return mainView;
    }

    public ITagsActivity getTagsActivity() {
        return tagsActivity;
    }

    public void setMainView(IMainActivity mainView) {
        this.mainView = mainView;
    }

    public void setTagsActivity(ITagsActivity tagsActivity) {
        this.tagsActivity = tagsActivity;
    }

    // Métodos destinados a la navegación en la aplicación y a la definición de servicios


    // Métodos de manejo de los componentes de Android
    public void launchActivity(Class actividadInvocada, Object invocador, Bundle extras) {
        Intent i = new Intent(this, actividadInvocada);
        if (extras != null)
            i.putExtras(extras);
        if (!invocador.getClass().equals(Activity.class))
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void launchActivityForResult(Class actividadInvocada,
                                        Activity actividadInvocadora, int requestCode, Bundle extras) {
        Intent i = new Intent(actividadInvocadora, actividadInvocada);
        if (extras != null)
            i.putExtras(extras);
        actividadInvocadora.startActivityForResult(i, requestCode);
    }

    public void launchService(Class servicioInvocado, Bundle extras) {
        Intent i = new Intent(this, servicioInvocado);
        if (extras != null)
            i.putExtras(extras);
        startService(i);
    }

    public void stopService(Class servicioInvocado) {
        Intent i = new Intent(this, servicioInvocado);
        stopService(i);
    }

    public void registerReceiver(BroadcastReceiver receptor, String accion) {
        LocalBroadcastManager.getInstance(this).registerReceiver(receptor, new IntentFilter(accion));
    }

    public void unRegisterReceiver(BroadcastReceiver receptor) {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receptor);
    }

    public void sendBroadcast(String accion, Bundle extras) {
        Intent intent = new Intent();
        intent.setAction(accion);
        if (extras != null)
            intent.putExtras(extras);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        presenter = null;
        singleton = this;
    }
}
