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
import com.example.panorama.presenter.IPresenterCamera;
import com.example.panorama.presenter.IPresenterImagePreview;
import com.example.panorama.presenter.IPresenterUploadImage;
import com.example.panorama.presenter.PresenterImageList;
import com.example.panorama.presenter.PresenterCamera;
import com.example.panorama.presenter.PresenterImagePreview;
import com.example.panorama.presenter.PresenterUploadImage;
import com.example.panorama.view.IActivityImageList;
import com.example.panorama.view.IActivityCamera;
import com.example.panorama.view.IActivityImagePreview;
import com.example.panorama.view.IActivityUploadImage;

import net.gotev.uploadservice.UploadService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by IvanGlez on 12/03/2018.
 */

public class Mediator extends Application {
    private static Mediator singleton;

    // Variables corresponding to views, presenters and models
    private IActivityCamera activityCamera;
    private IActivityUploadImage activityUploadImage;
    private IActivityImagePreview activityImagePreview;
    private IActivityImageList imageListActivity;
    private IPresenterCamera presenterCamera;
    private IPresenterUploadImage presenterUploadImage;
    private IPresenterImagePreview presenterImagePreview;
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

    public IActivityCamera getActivityCamera() {
        return activityCamera;
    }

    public IActivityUploadImage getActivityUploadImage() {
        return activityUploadImage;
    }

    public IActivityImagePreview getActivityImagePreview() {
        return activityImagePreview;
    }

    public IActivityImageList getActivityImageList() {
        return imageListActivity;
    }

    public void setActivityCamera(IActivityCamera activityCamera) {
        this.activityCamera = activityCamera;
    }

    public void setActivityUploadImage(IActivityUploadImage activityUploadImage) {
        this.activityUploadImage = activityUploadImage;
    }

    public void setActivityImagePreview(IActivityImagePreview activityImagePreview) {
        this.activityImagePreview = activityImagePreview;
    }

    public void setImageListActivity(IActivityImageList imageListActivity) {
        this.imageListActivity = imageListActivity;
    }

    //PRESENTERS

    public IPresenterCamera getPresenterCamera() {
        if (presenterCamera == null)
            presenterCamera = new PresenterCamera();
        return presenterCamera;
    }

    public IPresenterUploadImage getPresenterUploadImage() {
        if (presenterUploadImage == null)
            presenterUploadImage = new PresenterUploadImage();
        return presenterUploadImage;
    }

    public IPresenterImagePreview getPresenterImagePreview() {
        if (presenterImagePreview == null)
            presenterImagePreview = new PresenterImagePreview();
        return presenterImagePreview;
    }

    public IPresenterImageList getPresenterImageList() {
        if (presenterImageList == null)
            presenterImageList = new PresenterImageList();
        return presenterImageList;
    }

    public void removePresenterCamera() {
        presenterCamera = null;
    }

    public void removePresenterUploadImage() {
        presenterUploadImage = null;
    }

    public void removePresenterImagePreview() {
        presenterImagePreview = null;
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

        UploadService.NAMESPACE = "com.example.panorama";

        //DatabaseFacade.getInstance().deleteDatabase();
    }
}
