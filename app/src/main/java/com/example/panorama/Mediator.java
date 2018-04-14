package com.example.panorama;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.example.panorama.model.database.ModuleCustomTagsPanoramicImage;
import com.example.panorama.presenter.IPresenterImageList;
import com.example.panorama.presenter.IPresenterMainActivity;
import com.example.panorama.presenter.IPresenterPanoramaPreview;
import com.example.panorama.presenter.IPresenterUploadImage;
import com.example.panorama.presenter.PresenterImageList;
import com.example.panorama.presenter.PresenterMainActivity;
import com.example.panorama.presenter.PresenterPanoramaPreview;
import com.example.panorama.presenter.PresenterUploadImage;
import com.example.panorama.view.IActivityImageList;
import com.example.panorama.view.IActivityMain;
import com.example.panorama.view.IActivityPanoramaPreview;
import com.example.panorama.view.IActivityUploadImage;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by IvanGlez on 12/03/2018.
 */

public class Mediator extends Application {
    private static Mediator singleton;

    // Variables corresponding to views, presenters and models
    private IActivityMain mainView;
    private IActivityUploadImage activityUploadImage;
    private IActivityPanoramaPreview activityPanoramaPreview;
    private IActivityImageList imageListActivity;
    private IPresenterMainActivity presenterMainActivity;
    private IPresenterUploadImage presenterUploadImage;
    private IPresenterPanoramaPreview presenterPanoramaPreview;
    private IPresenterImageList presenterImageList;


    public static final String WEATHER_INFO = "com.example.panorama.WEATHER_INFO";
    public static final String WEATHER_DATA = "WEATHER_DATA";

    public static final String UNIT = "metric"; // Temperature units will be Celsius
    public static final String KEY = "100aa2ed5976d1aa9602749889a27156";  // OpenWeatherMap personal API Key



    public static Mediator getInstance(){
        return singleton;
    }



    ///////////////////////////// METHODS TO ACCESS VIEWS, PRESENTERS AND MODELS ////////////////////////////////////////////

    // VIEWS

    public IActivityMain getActivityMain() {
        return mainView;
    }

    public IActivityUploadImage getActivityUploadImage() {
        return activityUploadImage;
    }

    public IActivityPanoramaPreview getActivityPanoramaPreview() {
        return activityPanoramaPreview;
    }

    public IActivityImageList getActivityImageList() {
        return imageListActivity;
    }

    public void setMainView(IActivityMain mainView) {
        this.mainView = mainView;
    }

    public void setActivityUploadImage(IActivityUploadImage activityUploadImage) {
        this.activityUploadImage = activityUploadImage;
    }

    public void setActivityPanoramaPreview(IActivityPanoramaPreview activityPanoramaPreview) {
        this.activityPanoramaPreview = activityPanoramaPreview;
    }

    public void setImageListActivity(IActivityImageList imageListActivity) {
        this.imageListActivity = imageListActivity;
    }

    //PRESENTERS

    public IPresenterMainActivity getPresenterMainActivity() {
        if (presenterMainActivity == null)
            presenterMainActivity = new PresenterMainActivity();
        return presenterMainActivity;
    }

    public IPresenterUploadImage getPresenterUploadImage() {
        if (presenterUploadImage == null)
            presenterUploadImage = new PresenterUploadImage();
        return presenterUploadImage;
    }

    public IPresenterPanoramaPreview getPresenterPanoramaPreview() {
        if (presenterPanoramaPreview == null)
            presenterPanoramaPreview = new PresenterPanoramaPreview();
        return presenterPanoramaPreview;
    }

    public IPresenterImageList getPresenterImageList() {
        if (presenterImageList == null)
            presenterImageList = new PresenterImageList();
        return presenterImageList;
    }

    public void removePresenterMainActivity() {
        presenterMainActivity = null;
    }

    public void removePresenterUploadImage() {
        presenterUploadImage = null;
    }

    public void removePresenterPanoramaPreview() {
        presenterPanoramaPreview = null;
    }

    public void removePresenterImageList() {
        presenterImageList = null;
    }

    //////////////////////////////////// METHODS TO NAVIGATE BETWEEN ACTIVITIES /////////////////////////////////////////////////

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
        presenterUploadImage = null;
        singleton = this;
        Realm.init(this);

        // initialization of the database
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("panoramaimages.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .modules(new ModuleCustomTagsPanoramicImage())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        //DatabaseFacade.getInstance().deleteDatabase();
    }
}
