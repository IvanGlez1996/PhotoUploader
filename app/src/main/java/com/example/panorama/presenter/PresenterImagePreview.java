package com.example.panorama.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.panorama.Mediator;
import com.example.panorama.model.IModelImagePreview;
import com.example.panorama.model.ModelImagePreview;
import com.example.panorama.view.ActivityUploadImage;
import com.example.panorama.view.IActivityImagePreview;

import java.util.ArrayList;


public class PresenterImagePreview implements IPresenterImagePreview {

    private Mediator mediator;
    private IActivityImagePreview panoramaPreviewActivity;
    private IModelImagePreview model;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Mediator.WEATHER_INFO)){
                ArrayList<String> data = intent.getStringArrayListExtra(Mediator.WEATHER_DATA);
                if (data != null){
                    model.setWeatherTags(data);
                }
            }
            mediator.unRegisterReceiver(this);
        }
    };

    public PresenterImagePreview() {
        mediator = Mediator.getInstance();
        panoramaPreviewActivity = mediator.getActivityImagePreview();
        model = ModelImagePreview.getInstance();

    }

    @Override
    public void saveImageIntoDatabase(ArrayList<String> data){
        model.saveImageIntoDatabase(data);
    }

    @Override
    public void getWeatherInfo() {
        mediator.registerReceiver(receiver, Mediator.WEATHER_INFO);
    }

    @Override
    public void loadPhotoSphere(String filename) {
        Bitmap myBitmap = model.getPhotoSphere(filename);
        if (model.getImageFileExists()) {
            panoramaPreviewActivity.showImage(myBitmap);
        } else {
            Log.d("Error", "The image doesn't exist");
        }
    }

    @Override
    public void registerSensorListener(){
        model.registerSensorListener();
    }

    @Override
    public void unregisterSensorListener(){
        model.unregisterSensorListener();
    }

    @Override
    public void saveImage(){
        model.saveImage();
        goToActivityUploadImage();
    }

    @Override
    public void goToActivityUploadImage(){
        Toast.makeText(mediator.getApplicationContext(), "File saved at: " + panoramaPreviewActivity.getImagePath(), Toast.LENGTH_LONG).show();
        Bundle extras = new Bundle();
        extras.putString("imagePath", panoramaPreviewActivity.getImagePath());
        mediator.launchActivity(ActivityUploadImage.class, panoramaPreviewActivity, extras);
        panoramaPreviewActivity.finishActivity();
        mediator.removePresenterImagePreview();
    }

    @Override
    public boolean canGetLocation(){
        return model.canGetLocation();
    }

    @Override
    public void initGPS(){
        model.initGPSService();
    }

    @Override
    public void initSensors(){
        model.initSensorsService();
    }

    @Override
    public void setTagValues(){
        model.setTagsValues();
    }


}
