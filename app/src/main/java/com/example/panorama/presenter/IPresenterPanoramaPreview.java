package com.example.panorama.presenter;

import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;

import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.PanoramicImage;

import java.util.ArrayList;
import java.util.List;

public interface IPresenterPanoramaPreview {

    void saveImageIntoDatabase(ArrayList<String> data);

    void getWeatherInfo();

    void loadPhotoSphere(String filename);


    void registerSensorListener();

    void unregisterSensorListener();

    void setTagsValues();

    void setWeatherTags(ArrayList<String> data);

    void saveImage();
}

