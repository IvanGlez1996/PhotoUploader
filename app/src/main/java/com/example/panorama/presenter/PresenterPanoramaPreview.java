package com.example.panorama.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.panorama.Mediator;
import com.example.panorama.model.IModelPanoramaPreview;
import com.example.panorama.model.ModelPanoramaPreview;
import com.example.panorama.model.low_level.GPSTracker;
import com.example.panorama.view.ActivityUploadImage;
import com.example.panorama.view.IActivityPanoramaPreview;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class PresenterPanoramaPreview implements IPresenterPanoramaPreview, SensorEventListener {

    private Mediator mediator;
    private IActivityPanoramaPreview panoramaPreviewActivity;
    private IModelPanoramaPreview model;
    private SensorManager mSensorManager;
    private Sensor mLight;
    private Integer count;
    private GPSTracker gps;

    private ArrayList<String> tags;



    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Mediator.WEATHER_INFO)){
                ArrayList<String> data = intent.getStringArrayListExtra(Mediator.WEATHER_DATA);
                if (data != null){
                    setWeatherTags(data);
                }
            }
            mediator.unRegisterReceiver(this);
        }
    };

    public PresenterPanoramaPreview() {
        mediator = Mediator.getInstance();
        panoramaPreviewActivity = mediator.getActivityPanoramaPreview();
        model = ModelPanoramaPreview.getInstance();
        mSensorManager = (SensorManager) mediator.getSystemService(Context.SENSOR_SERVICE);

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
        count = 1;

        tags = new ArrayList<>();

        // create class object
        gps = new GPSTracker(mediator.getApplicationContext());

        if(!gps.canGetLocation()){

            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            panoramaPreviewActivity.showSettingsAlert();
        }

        loadPhotoSphere(panoramaPreviewActivity.getFilename());
        getWeatherInfo();
        setTagsValues();

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
        File imgFile = new File(filename);

        if (imgFile.exists()) {

            String path = imgFile.getAbsolutePath();
            Bitmap myBitmap = BitmapFactory.decodeFile(path);
            tags.add(path);

            panoramaPreviewActivity.showImage(myBitmap);
        } else {
            Log.d("Error", "The image doesn't exist");
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        if (event.sensor.equals(mLight) && count == 1) {
            count = 0;
            float lx = event.values[0];
            String lxStr = String.valueOf(lx) ;
            tags.add(lxStr);
        }

        mSensorManager.unregisterListener(this);

    }

    @Override
    public void registerSensorListener(){
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void unregisterSensorListener(){
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void setTagsValues() {


        //GPS

        // check if GPS enabled
        if(gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            String latitudeStr = String.valueOf(latitude);
            String longitudeStr = String.valueOf(longitude);

            tags.add(latitudeStr);
            tags.add(longitudeStr);

            Geocoder gcd = new Geocoder(mediator.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && addresses.size() > 0) {
                String locality = addresses.get(0).getLocality();
                tags.add(locality);
            }
        }

        //DATE & TIME

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        tags.add(date);

    }

    @Override
    public void setWeatherTags(ArrayList<String> data){
        tags.add(data.get(1));
        tags.add(data.get(2));
        tags.add(data.get(3));
        tags.add(data.get(4));
        tags.add(data.get(5));
        tags.add(data.get(6));

    }

    @Override
    public void saveImage(){
        while (true){
            if (tags.size() == 12) {
                saveImageIntoDatabase(tags);
                Toast.makeText(mediator.getApplicationContext(), "File saved at: " + panoramaPreviewActivity.getFilename(), Toast.LENGTH_LONG).show();
                Bundle extras = new Bundle();
                extras.putString("imagePath", tags.get(0));
                mediator.launchActivity(ActivityUploadImage.class, panoramaPreviewActivity, extras);
                gps.stopUsingGPS();
                unregisterSensorListener();
                panoramaPreviewActivity.finishActivity();
                break;
            }
        }
    }


}
