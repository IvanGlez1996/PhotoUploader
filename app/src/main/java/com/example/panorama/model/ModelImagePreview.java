package com.example.panorama.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.example.panorama.Mediator;
import com.example.panorama.model.database.DatabaseFacade;
import com.example.panorama.model.low_level.GPSTracker;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ModelImagePreview implements IModelImagePreview, SensorEventListener {

    private static ModelImagePreview singleton = null;
    private Mediator mediator;
    private DatabaseFacade database;
    private SensorManager mSensorManager;
    private Sensor mLight;
    private Integer count;
    private GPSTracker gps;
    private boolean imgFileExists = false;

    private ArrayList<String> tags = new ArrayList<>();

    private ModelImagePreview() {
        mediator = Mediator.getInstance();
        database = DatabaseFacade.getInstance();
    }

    public static ModelImagePreview getInstance() {
        if (singleton == null)
            singleton = new ModelImagePreview();
        return singleton;
    }

    @Override
    public void saveImageIntoDatabase(ArrayList<String> data){
        database.addPanoramicImageToDatabase(data.get(0), data.get(3), data.get(4), data.get(1), data.get(2), data.get(5), data.get(6), data.get(7), data.get(8), data.get(9), data.get(10), data.get(11));
    }


    @Override
    public Bitmap getPhotoSphere(String filename) {
        Bitmap result;
        File imgFile = new File(filename);

        if (imgFile.exists()) {
            setImageFileExists(true);
            String path = imgFile.getAbsolutePath();
            Bitmap myBitmap = BitmapFactory.decodeFile(path);
            tags.add(path);

            result = myBitmap;
        } else {
            Log.d("Error", "The image doesn't exist");
            result = null;
        }
        return result;
    }

    @Override
    public boolean getImageFileExists(){
        return imgFileExists;
    }

    @Override
    public void setImageFileExists(boolean imgFileExists){
        this.imgFileExists = imgFileExists;
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

        unregisterSensorListener();
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
                gps.stopUsingGPS();
                unregisterSensorListener();
                break;
            }
        }
    }

    @Override
    public void initSensorsService(){
        mSensorManager = (SensorManager) mediator.getSystemService(Context.SENSOR_SERVICE);

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
        count = 1;
    }

    @Override
    public void initGPSService(){
        // create class object
        if(gps == null) {
            gps = new GPSTracker(mediator.getApplicationContext());
        }
    }

    @Override
    public boolean canGetLocation(){
        boolean result = false;

        if(gps.canGetLocation()){
            result = true;
        }
        return result;
    }
}
